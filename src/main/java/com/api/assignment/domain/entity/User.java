package com.api.assignment.domain.entity;

import com.api.assignment.domain.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;
    private String userId;
    private String userPwd;
    private String userName;
    private int userPoint;

    public User( UserDto userDto ){

        if( StringUtils.hasText( userDto.getUserNo() ) ){
            setUserNo( Long.parseLong( userDto.getUserNo() ) );
        }

        setUserId( userDto.getUserId() );
        setUserPwd( userDto.getUserPwd() );
        setUserName( userDto.getUserName() );

        if( StringUtils.hasText( userDto.getUserPoint() ) ){
            setUserPoint( Integer.parseInt( userDto.getUserPoint() ) );
        }
    }

    public UserDto entityToDto(){
        UserDto userDto = new UserDto();

        userDto.setUserNo( Long.toString( getUserNo() ) );
        userDto.setUserId( getUserId() );
        userDto.setUserName( getUserName() );
        userDto.setUserPoint( Integer.toString( getUserPoint() ) );

        return userDto;
    }
}
