package com.api.assignment.service;

import com.api.assignment.domain.dto.ArticleDto;
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
public class ArticleService {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    /**
     * 글 쓰기
     * */
    @Transactional
    public Long articleWrite( ArticleDto articleDto ) throws ApiRuntimeException{
        // 1. 필수 데이터 확인
        if( !StringUtils.hasText( articleDto.getArticleTitle() )
                || !StringUtils.hasText( articleDto.getArticleContents() )
                || !StringUtils.hasText( articleDto.getUserNo() ) ){
            throw new ApiRuntimeException("필수 요청 데이터가 누락되었습니다.");
        }

        // 2. 사용자 정보 조회
        User findUser = userRepository.findOne( Long.parseLong( articleDto.getUserNo() ) );

        // 3. 엔티티 설정
        Article article = new Article( articleDto, findUser );

        // 4. 저장
        Long articleNo = articleRepository.save( article );

        // 5. 사용자 포인트 3 증가
        findUser.setUserPoint( findUser.getUserPoint() + 3 );

        return articleNo;
    }

    /**
     * 글 수정
     * */
    @Transactional
    public Long articleModify( ArticleDto articleDto ) throws ApiRuntimeException{
        // 1. 필수 데이터 확인
        if( !StringUtils.hasText( articleDto.getArticleId() )
                || !StringUtils.hasText(articleDto.getArticleTitle() )
                || !StringUtils.hasText(articleDto.getArticleContents() ) ){
            throw new ApiRuntimeException("필수 요청 데이터가 누락되었습니다.");
        }

        // 2. 조회
        Article article = articleRepository.findOne( Long.parseLong( articleDto.getArticleId() ) );

        // 3. 설정
        article.setArticleTitle( articleDto.getArticleTitle() );
        article.setArticleContents( articleDto.getArticleContents() );

        return article.getArticleNo();
    }


    /**
     * 글 조회
     * */
    @Transactional
    public ArticleDto getArticleInfo( String articleNo ) throws ApiRuntimeException{
        // 1. 필수 데이터 확인
        if( !StringUtils.hasText( articleNo ) ){
            throw new ApiRuntimeException("필수 요청 데이터가 누락되었습니다.");
        }

        // 2. 글 정보 조회
        Article article = articleRepository.findOne( Long.parseLong( articleNo ) );
        ArticleDto result = article.entityToDto();

        // 3. 댓글 정보 조회 및 설정
        List<Comment> commentList = commentRepository.findAllByArticleNo( Long.parseLong( articleNo ) );
        List<String> commentsIdList = new ArrayList<>();
        if( commentList != null && commentList.size() > 0 ){
            for( Comment comment : commentList ){
                commentsIdList.add( Long.toString( comment.getCommentNo() ) );
            }

            result.setCommentsIdList( commentsIdList );
        }

        return result;
    }


    /**
     * 글 삭제
     * */
    @Transactional
    public int articleRemove( String articleNo ) throws ApiRuntimeException{
        // 1. 필수 데이터 확인
        if( !StringUtils.hasText( articleNo ) ){
            throw new ApiRuntimeException("필수 요청 데이터가 누락되었습니다.");
        }

        // 2. 조회
        Article article = articleRepository.findOne( Long.parseLong( articleNo ) );
        User articleWriter = article.getUser(); // -3 포인트

        // 3. 댓글 정보 조회 및 설정
        List<Comment> commentList = commentRepository.findAllByArticleNo( Long.parseLong( articleNo ) );

        // 3. 댓글 삭제
        if( commentList != null && commentList.size() > 0 ){
            for( Comment comment : commentList ){
                User commentWriter = comment.getUser();

                // 댓글 삭제
                commentRepository.removeOne( comment );

                // 댓글 포인트 제거
                articleWriter.setUserPoint( articleWriter.getUserPoint() - 1 ); // 원글 작성자 포인트
                commentWriter.setUserPoint( commentWriter.getUserPoint() - 2 ); // 댓글 작성자 포인트
            }
        }

        // 4. 글 삭제
        articleRepository.removeOne( article );

        // 5. 포인트 삭제
        articleWriter.setUserPoint( articleWriter.getUserPoint() - 3 ); // 작성자 포인트

        return 1;
    }
}
