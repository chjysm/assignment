package com.api.assignment.repository;

import com.api.assignment.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    @PersistenceContext
    private final EntityManager em;

    // 저장
    public Long save( User user ){
        if( user.getUserNo() == null ){
            em.persist(user);
        } else {
            em.merge(user);
        }
        return user.getUserNo();
    }

    // 조회(단건-사용자 번호)
    public User findOne( Long userNo ){
        return em.find( User.class, userNo );
    }

    // 조회(단건-사용자ID)
    public List<User> findById(String userId) {
        return em.createQuery("select m from User m where m.userId = :userId",
                        User.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    // 조회(단건-사용자ID and 사용자패스워드)
    public User findByIdAndPwd(User user) {
        return em.createQuery("select m from User m where m.userId = :userId and m.userPwd = :userPwd",
                        User.class)
                .setParameter("userId", user.getUserId())
                .setParameter("userPwd", user.getUserPwd())
                .getSingleResult();
    }

}
