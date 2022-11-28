package com.api.assignment.repository;

import com.api.assignment.domain.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArticleRepository {

    @PersistenceContext
    private final EntityManager em;

    // 저장
    public Long save( Article article ){
        if( article.getArticleNo() == null ){
            em.persist(article);
        } else {
            em.merge(article);
        }
        return article.getArticleNo();
    }

    // 조회
    public Article findOne( Long articleNo ) {
        return em.find( Article.class, articleNo );
    }

    // 삭제
    public void removeOne( Article article ) {
        em.remove( article );
    }

}
