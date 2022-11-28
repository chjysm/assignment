package com.api.assignment.service;

import com.api.assignment.domain.dto.UserDto;
import com.api.assignment.domain.entity.User;
import com.api.assignment.repository.UserRepository;
import com.api.assignment.util.JwtTokenProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Test
    void 회원가입_테스트(){
        // given
        UserDto user = new UserDto();
        user.setUserId("testId");
        user.setUserPwd("testPwd");
        user.setUserName("testName");

        // when
        long userNo = userService.join(user);
        User findUser = userRepository.findOne(userNo);

        // then
        Assertions.assertThat( user.getUserId().equals( findUser.getUserId() ) );
    }

    @Test
    void 로그인_테스트(){
        // given
        UserDto user = new UserDto();
        user.setUserId("testId");
        user.setUserPwd("testPwd");
        user.setUserName("testName");
        long userNo = userService.join(user);

        // when
        String token = userService.login(user);
        String tokenPk = jwtTokenProvider.getUserPk(token);

        // then
        Assertions.assertThat( Long.toString( userNo ).equals( tokenPk ) );
    }

    @Test
    void 내정보조회_테스트(){
        // given
        UserDto user = new UserDto();
        user.setUserId("testId");
        user.setUserPwd("testPwd");
        user.setUserName("testName");
        long userNo = userService.join(user);

        // when
        UserDto userProfile = userService.getUserProfile( Long.toString( userNo ) );

        // then
        Assertions.assertThat( user.getUserName().equals( userProfile.getUserName() ) );
    }
}