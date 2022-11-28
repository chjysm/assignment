package com.api.assignment.service;

import com.api.assignment.domain.dto.UserDto;
import com.api.assignment.domain.entity.User;
import com.api.assignment.exception.ApiRuntimeException;
import com.api.assignment.repository.UserRepository;
import com.api.assignment.util.Encorder;
import com.api.assignment.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final Encorder encorder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(UserDto userDto) throws ApiRuntimeException {
        User user = new User( userDto );

        // 1. 중복 확인
        validateDuplicateUserWithId( user );

        // 2. 패스워드 단방향 암호화
        user.setUserPwd( encorder.sha256Encoding( user.getUserPwd() ) );

        // 3. 저장
        userRepository.save( user );

        return user.getUserNo();
    }

    /**
     * 로그인
     * */
    @Transactional
    public String login(UserDto userDto) throws ApiRuntimeException {
        User user = new User( userDto );

        // 1. 필수 데이터 확인
        if( !StringUtils.hasText( user.getUserId() )
            || !StringUtils.hasText( user.getUserPwd() ) ){
            throw new ApiRuntimeException("필수 요청 데이터가 누락되었습니다.");
        }

        // 2. 조회
        List<User> findUser = userRepository.findById(user.getUserId());

        if ( findUser.isEmpty() ) {
            throw new ApiRuntimeException("사용자 미존재");
        }

        if ( !encorder.sha256Matching( user.getUserPwd(), findUser.get(0).getUserPwd() ) ){
            throw new ApiRuntimeException("패스워드 불일치");
        }

        return jwtTokenProvider.createToken( findUser.get(0).getUserNo().toString(), Collections.singletonList("ROLE_USER") );
    }

    /**
     *   중복 회원 확인( ID )
     */
    private void validateDuplicateUserWithId( User user ) throws ApiRuntimeException {
        List<User> findUsers = userRepository.findById( user.getUserId() );

        if ( !findUsers.isEmpty() ) {
            throw new ApiRuntimeException("사용자 ID 가 중복 되었습니다.");
        }
    }

    /**
     * 내 정보 조회
     * */
    public UserDto getUserProfile( String userNo ) throws ApiRuntimeException {
        // 1. 필수 데이터 확인
        if( !StringUtils.hasText( userNo ) ){
            throw new ApiRuntimeException("필수 요청 데이터가 누락되었습니다.");
        }

        User findUser = userRepository.findOne( Long.parseLong( userNo ) );
        return findUser.entityToDto();
    }

    /**
     * 내 Points 조회
     * */
    public UserDto getPoints( String userNo ) throws ApiRuntimeException {
        // 1. 필수 데이터 확인
        if( !StringUtils.hasText( userNo ) ){
            throw new ApiRuntimeException("필수 요청 데이터가 누락되었습니다.");
        }

        User findUser = userRepository.findOne( Long.parseLong( userNo ) );
        UserDto result = new UserDto();
        result.setUserPoint( Integer.toString( findUser.getUserPoint() ) );
        return result;
    }

}
