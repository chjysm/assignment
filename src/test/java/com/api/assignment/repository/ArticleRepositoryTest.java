package com.api.assignment.repository;

import com.api.assignment.domain.entity.Article;
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
class ArticleRepositoryTest {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional
    @Test
    void 글_저장_조회_테스트() throws Exception{
        // given
        User user = new User();
        user.setUserId("testId");
        user.setUserPwd("testPwd");
        user.setUserName("testName");
        Long userNo = userRepository.save(user);

        Article article = new Article();
        article.setArticleTitle("testTitle");
        article.setArticleContents("testContents");
        article.setUser(user);

        // when
        Long articleId = articleRepository.save(article);
        Article findArticle = articleRepository.findOne(articleId);

        // then
        Assertions.assertThat( article.equals( findArticle ) );
    }

}