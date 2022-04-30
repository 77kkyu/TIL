package com.example.jpa_persistence_context.entityTest;

import com.example.jpa_persistence_context.domain.Member;
import com.example.jpa_persistence_context.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;
import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
        try {
            transaction.begin();
            Member member = Member.builder() // 비영속(new/transient)
                    .name("testId")
                    .build();
            entityManager.persist(member); // 영속성 컨텍스트에 저장
            transaction.commit(); // 커밋 flush() 자동 호출
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        entityManagerFactory.close();
    }

    @Transactional
    @Test
    public void 준영속성테스트() {

        // 영속성 컨텍스트가 관리하던 영속 상태의 엔티티 더이상 관리하지 않으면 준영속 상태가 된다.
        // 특정 엔티티를 준영속 상태로 만드려면 em.datach()를 호출하면 된다.

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("member");
        EntityManager em = emf.createEntityManager();

        em.find(Member.class, 3L);
        Member findMember = em.find(Member.class, 3L);
        em.detach(findMember); // 영속성 컨테스트에 분리해 준영속성으로 변경 된다.
        // em.claer(); // 영속성 콘텍스트를 비워도 관리되던 엔티티는 준영속 상태가 된다.
        // em.close(); // 영속성 콘텍스트를 종료해도 관리되던 엔티티는 준영속 상태가 된다.

    }

    @Test
    public void 조회테스트() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("member");
        EntityManager em = emf.createEntityManager();

        Member member1 = entityManager.find(Member.class, 4L);
        System.out.println(member1.getName());
        System.out.println(member1);

    }

    @Transactional
    @Test
    public void 삭제테스트() {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("member");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Member member = entityManager.find(Member.class, 4L);
            entityManager.remove(member);
            transaction.commit(); // 커밋
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        entityManagerFactory.close();

    }

    @Test
    public void 동일성테스트() {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("member");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Member member1 = entityManager.find(Member.class, 5L);
        Member member2 = entityManager.find(Member.class, 5L);

        System.out.println(member1 == member2);

    }

}
