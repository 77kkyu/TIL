package com.example.jpa_persistence_context.entityTest;

import com.example.jpa_persistence_context.domain.Member;
import com.example.jpa_persistence_context.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;
import javax.transaction.Transactional;

@SpringBootTest
public class MemberEntityManager {

    @Autowired
    private MemberRepository memberRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Test
    public void 데이터저장_영속성() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("member");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try{
            Member member = Member.builder() // 비영속(new/transient)
                    .name("77kkyu111")
                    .build();
            entityManager.persist(member); // 영구저장
            transaction.commit(); // 커밋
        } catch (Exception e){
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        entityManagerFactory.close();
    }

    @Test
    public void 준영속성테스트() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("member");
        EntityManager em = emf.createEntityManager();

        long beforeTime = System.currentTimeMillis();
        em.find(Member.class, 3L);
        long afterTime = System.currentTimeMillis();
        long secDiffTime = (afterTime - beforeTime)/1000;
        System.out.println("시간차이(m) : "+secDiffTime);

        Member findMember = em.find(Member.class, 3L);
        em.detach(findMember); // 영속성 컨테스트에 분리해 준영속성으로 변경
        long beforeTime1 = System.currentTimeMillis();
        em.find(Member.class, 3L);
        long afterTime1 = System.currentTimeMillis();
        long secDiffTime1 = (afterTime1 - beforeTime1)/1000;
        System.out.println("시간차이(m)1 : "+secDiffTime1);

    }

    @Transactional
    @Test
    public void 맴버영속성테스트() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("member");
        EntityManager em = emf.createEntityManager();

        Member member = Member.builder() // 비영속(new/transient)
                .name("77kkyu")
                .build();

        Member memberSave = memberRepository.save(member);
        entityManager.persist(memberSave);
        //em.persist(member); // 영속
        //Member a = em.find(Member.class, memberSave.getId());
        //System.out.println("aaa : " + a.getName());

    }

    @Test
    public void 조회테스트() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("member");
        EntityManager em = emf.createEntityManager();

        //Member member = em.find(Member.class, 13L);
        Member member1 = entityManager.find(Member.class, 14L);
        System.out.println(member1.getName());

    }

    @Test
    public void 삭제테스트() {

    }


}
