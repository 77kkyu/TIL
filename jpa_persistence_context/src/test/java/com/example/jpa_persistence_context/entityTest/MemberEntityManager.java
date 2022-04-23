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
    public void 데이터저장() {
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
        }catch (Exception e){
            transaction.rollback();
        }finally {
            entityManager.close();
        }
        entityManagerFactory.close();
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
    public void 준영속성테스트() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Member");
        EntityManager em = emf.createEntityManager();

        Member member = Member.builder() // 비영속(new/transient)
                .name("77kkyu")
                .build();

        em.detach(member);

    }

    @Test
    public void 삭제테스트() {

    }


}
