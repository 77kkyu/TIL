package com.example.jpalock.optimissticLock.service;

import com.example.jpalock.optimissticLock.entity.Member;
import com.example.jpalock.optimissticLock.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Getter
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(Transactional.TxType.REQUIRES_NEW) // 새로운 트랜잭션 생성
    public void memberUpdate(Member member) {
        entityManager.merge(member);
        entityManager.flush();
    }

}
