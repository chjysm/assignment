package com.api.assignment.domain.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements UserDetails {
    @ApiModelProperty(example = "사용자 번호" )
    private String userNo;
    @ApiModelProperty(example = "사용자 ID" )
    private String userId;
    @ApiModelProperty(example = "사용자 비밀번호" )
    private String userPwd;
    @ApiModelProperty(example = "사용자 명" )
    private String userName;
    @ApiModelProperty(example = "사용자 포인트")
    private String userPoint;
    @ApiModelProperty(hidden = true)
    private List<String> roles = new ArrayList<>();


    @Override
    @ApiModelProperty(hidden = true)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    @ApiModelProperty(hidden = true)
    public String getPassword() {
        return userPwd;
    }

    @Override
    @ApiModelProperty(hidden = true)
    public String getUsername() {
        return userNo;
    }

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @ApiModelProperty(hidden = true)
    public boolean isEnabled() {
        return true;
    }
    @ApiModelProperty(hidden = true)
    public String getUserName() {
        return userName;
    }
}
