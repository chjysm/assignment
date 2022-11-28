package com.api.assignment.service;

import com.api.assignment.domain.dto.ArticleDto;
import com.api.assignment.domain.dto.CommentDto;
import com.api.assignment.domain.entity.Article;
import com.api.assignment.domain.entity.Comment;
import com.api.assignment.domain.entity.User;
import com.api.assignment.repository.ArticleRepository;
import com.api.assignment.repository.CommentRepository;
import com.api.assignment.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class CommentServiceTest {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;

    @Test
    public void 댓글_작성_테스트() throws Exception{
        // given
        User user = new User();
        user.setUserId("testId");
        user.setUserPwd("testPwd");
        user.setUserName("testName");
        long userNo = userRepository.save(user);

        User user2 = new User();
        user2.setUserId("testId2");
        user2.setUserPwd("testPwd2");
        user2.setUserName("testName2");
        long userNo2 = userRepository.save(user2);

        Article article = new Article();
        article.setArticleTitle("testTitle");
        article.setArticleContents("testContents");
        article.setUser( user );
        Long articleId = articleRepository.save(article);

        CommentDto commentDto = new CommentDto();
        commentDto.setUserNo( Long.toString( userNo2 ) );
        commentDto.setArticleId( Long.toString( articleId ) );
        commentDto.setCommentsContents("testComment");

        // when
        CommentDto result = commentService.commentWrite(commentDto);
        Comment findComment = commentRepository.findOne(Long.parseLong(result.getCommentsId()));

        // then
        Assertions.assertThat( findComment.getCommentContents().equals( commentDto.getCommentsContents() ) );
    }

    @Test
    public void 댓글_삭제_테스트() throws Exception{
        // given
        User user = new User();
        user.setUserId("testId");
        user.setUserPwd("testPwd");
        user.setUserName("testName");
        long userNo = userRepository.save(user);

        User user2 = new User();
        user2.setUserId("testId2");
        user2.setUserPwd("testPwd2");
        user2.setUserName("testName2");
        long userNo2 = userRepository.save(user2);

        Article article = new Article();
        article.setArticleTitle("testTitle");
        article.setArticleContents("testContents");
        article.setUser( user );
        Long articleId = articleRepository.save(article);

        Comment comment = new Comment();
        comment.setCommentContents("testComment");
        comment.setUser( user2 );
        comment.setArticle( article );
        Long commentId = commentRepository.save(comment);

        // when
        ArticleDto result = commentService.commentRemove(Long.toString(commentId));

        // then
        Assertions.assertThat( result.getArticleId().equals( Long.toString( articleId ) ) );

    }

}