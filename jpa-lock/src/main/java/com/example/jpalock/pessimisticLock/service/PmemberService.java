package com.example.jpalock.pessimisticLock.service;

import com.example.jpalock.pessimisticLock.entity.Pmember;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class PmemberService {

    @Getter
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateMember(Long id) {
        entityManager.setProperty("javax.persistence.query.timeout", 10000); // 락을 걸어놨기 때문에 타임아웃 설정
        Pmember pmember = entityManager.find(Pmember.class, id);
        pmember.setName("77kkyu01");
    }

}
