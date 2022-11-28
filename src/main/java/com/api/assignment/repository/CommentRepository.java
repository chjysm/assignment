package com.api.assignment.repository;

import com.api.assignment.domain.entity.Comment;
import com.api.assignment.domain.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    // 저장
    public Long save( Comment comment ){
        em.persist(comment);
        return comment.getCommentNo();
    }

    // 조회
    public Comment findOne( Long commentNo ) {
        return em.find( Comment.class, commentNo );
    }

    // 조회 (글 기준)
    public List<Comment> findAllByArticleNo(Long articleNo ) {
        return em.createQuery("select m from Comment m inner join m.article s where s.articleNo = :articleNo", Comment.class )
                .setParameter("articleNo", articleNo)
                .getResultList();
    }

    // 삭제
    public void removeOne( Comment comment ) {
        em.remove( comment );
    }

}
