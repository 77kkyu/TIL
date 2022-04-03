package com.example.jpalock.pessimisticLock;

import com.example.jpalock.optimissticLock.repository.MemberRepository;
import com.example.jpalock.optimissticLock.service.MemberService;
import com.example.jpalock.pessimisticLock.entity.Pmember;
import com.example.jpalock.pessimisticLock.repository.PmemberRepository;
import com.example.jpalock.pessimisticLock.service.PmemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.fail;

@SpringBootTest
public class PessimisticLockTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PmemberService pmemberService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PmemberRepository pmemberRepository;

    @Test
    public void 비관적락Insert() {
        Pmember pmember = pmemberRepository.save(
                Pmember.builder()
                        .name("77kkyu")
                        .build()
        );
        assertThat(pmember).isNotNull();
    }

    @Test
    @Transactional
    public void 비관적락예외() {
        entityManager.find(Pmember.class, 3L, LockModeType.PESSIMISTIC_WRITE); // lock
        // LockModeType.PESSIMISTIC_READ 반복 읽기만 가능
        // LockModeType.PESSIMISTIC_FORCE_INCREMENT Version과 관련
        entityManager.flush();
        // 해당 row에 lock이 설정되어 있어 오류 발생
        pmemberService.updateMember(3L);

        fail("Timeout Trying To Lock Table");
    }

}
