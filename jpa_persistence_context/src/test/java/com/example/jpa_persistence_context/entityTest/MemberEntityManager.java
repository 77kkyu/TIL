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
            entityManager.persist(member); // 영구저장
            transaction.commit(); // 커밋
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

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("member");
        EntityManager em = emf.createEntityManager();

        try {

            long beforeTime = System.currentTimeMillis();
            em.find(Member.class, 3L);
            long afterTime = System.currentTimeMillis();
            long secDiffTime = (afterTime - beforeTime)/1000;
            System.out.println("시간차이(m) : "+secDiffTime);

            Member findMember = em.find(Member.class, 3L);
            em.detach(findMember); // 영속성 컨테스트에 분리해 준영속성으로 변경
            // em.claer(); // 영속성 콘텍스트를 비워도 관리되던 엔티티는 준영속 상태가 된다.
            // em.close(); // 영속성 콘텍스트를 종료해도 관리되던 엔티티는 준영속 상태가 된다.
            long beforeTime1 = System.currentTimeMillis();
            em.find(Member.class, 3L);
            long afterTime1 = System.currentTimeMillis();
            long secDiffTime1 = (afterTime1 - beforeTime1)/1000;
            System.out.println("시간차이(m)1111 : "+secDiffTime1);

        } catch (Exception e) {

        }

    }

    @Test
    public void 조회테스트() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("member");
        EntityManager em = emf.createEntityManager();

        //Member member = em.find(Member.class, 13L);
        Member member1 = entityManager.find(Member.class, 14L);
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
