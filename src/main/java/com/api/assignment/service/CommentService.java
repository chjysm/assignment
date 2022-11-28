package com.api.assignment.service;

import com.api.assignment.domain.dto.ArticleDto;
import com.api.assignment.domain.dto.CommentDto;
import com.api.assignment.domain.entity.Article;
import com.api.assignment.domain.entity.Comment;
import com.api.assignment.domain.entity.User;
import com.api.assignment.exception.ApiRuntimeException;
import com.api.assignment.repository.ArticleRepository;
import com.api.assignment.repository.CommentRepository;
import com.api.assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    /**
     * 댓글 작성
     * */
    @Transactional
    public CommentDto commentWrite( CommentDto commentDto ) throws ApiRuntimeException {
        // 1. 필수 데이터 확인
        if( !StringUtils.hasText( commentDto.getCommentsContents() )
                || !StringUtils.hasText( commentDto.getUserNo() )
                || !StringUtils.hasText( commentDto.getArticleId() ) ){
            throw new ApiRuntimeException("필수 요청 데이터가 누락되었습니다.");
        }

        // 2. 사용자 정보 조회
        User findUser = userRepository.findOne( Long.parseLong( commentDto.getUserNo() ) );

        // 3. 글 정보 조회
        Article findArticle = articleRepository.findOne( Long.parseLong( commentDto.getArticleId()) );
        User articleWriter = findArticle.getUser();

        // 4. 엔티티 설정
        Comment comment = new Comment( commentDto, findUser, findArticle );

        // 5. 저장
        Long commentNo = commentRepository.save( comment );

        // 6. 사용자 포인트 증가
        findUser.setUserPoint( findUser.getUserPoint() + 2 );
        articleWriter.setUserPoint( articleWriter.getUserPoint() + 1 );

        CommentDto result = comment.entityToDto();
        result.setArticleId( Long.toString( findArticle.getArticleNo() ) );

        return result;
    }

    /**
     * 댓글 삭제
     * */
    @Transactional
    public ArticleDto commentRemove( String commentNo ) throws ApiRuntimeException {
        // 1. 필수 데이터 확인
        if (!StringUtils.hasText(commentNo)) {
            throw new ApiRuntimeException("필수 요청 데이터가 누락되었습니다.");
        }

        // 2. 댓글 조회
        Comment findComment = commentRepository.findOne(Long.parseLong(commentNo));
        User commentWriter = findComment.getUser();
        Article article = findComment.getArticle();
        User articleWriter = article.getUser();

        // 3. 댓글 삭제
        commentRepository.removeOne(findComment);

        // 4. 포인트 삭제
        articleWriter.setUserPoint(articleWriter.getUserPoint() - 1); // 원글 작성자 포인트
        commentWriter.setUserPoint(commentWriter.getUserPoint() - 2); // 댓글 작성자 포인트

        ArticleDto result = article.entityToDto();

        List<Comment> commentList = commentRepository.findAllByArticleNo( article.getArticleNo() );
        List<String> commentsIdList = new ArrayList<>();
        if( commentList != null && commentList.size() > 0 ){
            for( Comment comment : commentList ){
                commentsIdList.add( Long.toString( comment.getCommentNo() ) );
            }

            result.setCommentsIdList( commentsIdList );
        }

        return result;
    }
}
