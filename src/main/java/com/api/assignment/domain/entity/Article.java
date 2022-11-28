package com.api.assignment.domain.entity;

import com.api.assignment.domain.dto.ArticleDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Article {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleNo;
    private String articleTitle;
    private String articleContents;

    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn(name = "user_No")
    private User user;

    public Article( ArticleDto articleDto, User user ){
        if( StringUtils.hasText( articleDto.getArticleId() ) ){
            setArticleNo( Long.parseLong( articleDto.getArticleId() ) );
        }

        setArticleTitle( articleDto.getArticleTitle() );
        setArticleContents( articleDto.getArticleContents() );
        setUser( user );
    }

    public ArticleDto entityToDto(){
        ArticleDto articleDto = new ArticleDto();

        articleDto.setArticleId( Long.toString( getArticleNo() ) );
        articleDto.setArticleTitle( getArticleTitle() );
        articleDto.setArticleContents( getArticleContents() );

        return articleDto;
    }
}
