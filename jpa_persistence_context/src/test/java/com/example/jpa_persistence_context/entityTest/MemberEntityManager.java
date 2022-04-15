package com.example.jpa_persistence_context.entityTest;

import com.example.jpa_persistence_context.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;

@SpringBootTest
public class MemberEntityManager {

    @Test
    public void 맴버영속성테스트() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("member");
        EntityManager em = emf.createEntityManager();

        Member member = Member.builder() // 비영속(new/transient)
                .name("77kkyu")
                .build();

        em.persist(member); // 영속
    }

}
