package com.api.assignment.service;

import com.api.assignment.domain.dto.ArticleDto;
import com.api.assignment.domain.entity.Article;
import com.api.assignment.domain.entity.User;
import com.api.assignment.repository.ArticleRepository;
import com.api.assignment.repository.UserRepository;
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
class ArticleServiceTest {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ArticleService articleService;

    @Test
    void 글_쓰기_테스트() throws Exception  {
        // given
        User user = new User();
        user.setUserId("testId");
        user.setUserPwd("testPwd");
        user.setUserName("testName");
        long userNo = userRepository.save(user);

        ArticleDto article = new ArticleDto();
        article.setArticleTitle("testTitle");
        article.setArticleContents("testContents");
        article.setUserNo( Long.toString(userNo) );

        // when
        Long articleId = articleService.articleWrite(article);
        Article findArticle = articleRepository.findOne(articleId);

        // then
        Assertions.assertThat( article.getArticleTitle().equals( findArticle.getArticleTitle() ) );
    }

    @Test
    void 글_수정_테스트() throws Exception  {
        // given
        User user = new User();
        user.setUserId("testId");
        user.setUserPwd("testPwd");
        user.setUserName("testName");
        long userNo = userRepository.save(user);

        ArticleDto article = new ArticleDto();
        article.setArticleTitle("testTitle");
        article.setArticleContents("testContents");
        article.setUserNo( Long.toString(userNo) );

        Long articleId = articleService.articleWrite(article);
        article.setArticleId( Long.toString(articleId) );
        article.setArticleTitle("test2");
        article.setArticleContents("test2");

        // when
        articleId = articleService.articleModify(article);
        Article findArticle = articleRepository.findOne(articleId);

        // then
        Assertions.assertThat( article.getArticleTitle().equals( findArticle.getArticleTitle() ) );
    }

    @Test
    void 글_조회_테스트() throws Exception  {
        // given
        User user = new User();
        user.setUserId("testId");
        user.setUserPwd("testPwd");
        user.setUserName("testName");
        long userNo = userRepository.save(user);

        Article article = new Article();
        article.setArticleTitle("testTitle");
        article.setArticleContents("testContents");
        article.setUser( user );
        Long articleId = articleRepository.save(article);

        // when
        ArticleDto articleInfo = articleService.getArticleInfo(Long.toString(articleId));

        // then
        Assertions.assertThat( article.getArticleTitle().equals( articleInfo.getArticleTitle() ) );
    }

    @Test
    void 글_삭제_테스트() throws Exception  {
        // given
        User user = new User();
        user.setUserId("testId");
        user.setUserPwd("testPwd");
        user.setUserName("testName");
        long userNo = userRepository.save(user);

        Article article = new Article();
        article.setArticleTitle("testTitle");
        article.setArticleContents("testContents");
        article.setUser( user );
        Long articleId = articleRepository.save(article);

        // when
        int i = articleService.articleRemove(Long.toString(articleId));

        // then
        Assertions.assertThat( i == 1 );
    }


}