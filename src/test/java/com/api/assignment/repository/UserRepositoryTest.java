package com.api.assignment.repository;

import com.api.assignment.domain.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserRepositoryTest {
    @Autowired UserRepository userRepository;

    @Test
    @Transactional
    void 사용자_저장_및_조회_테스트() throws Exception {
        // given
        User user = new User();
        user.setUserId("testId");
        user.setUserPwd("testPwd");
        user.setUserName("testName");

        // when
        Long userNo = userRepository.save(user);
        User findUser = userRepository.findOne(userNo);

        // then
        Assertions.assertThat( findUser.equals( user ) );
    }

}