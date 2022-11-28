package com.api.assignment.domain.entity;

import com.api.assignment.domain.dto.CommentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentNo;
    private String commentContents;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn(name = "user_No")
    private User user;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn(name = "article_No")
    private Article article;

    public Comment( CommentDto commentDto, User user, Article article ){
        if( StringUtils.hasText( commentDto.getCommentsId() ) ){
            setCommentNo( Long.parseLong( commentDto.getArticleId() ) );
        }
        setCommentContents( commentDto.getCommentsContents() );
        setUser( user );
        setArticle( article );
    }

    public CommentDto entityToDto(){
        CommentDto commentDto = new CommentDto();

        commentDto.setCommentsId( Long.toString( getCommentNo() ) );
        commentDto.setCommentsContents( getCommentContents() );

        return commentDto;
    }
}
