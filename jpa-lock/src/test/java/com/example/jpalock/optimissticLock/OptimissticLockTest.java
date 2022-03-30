package com.example.jpalock.optimissticLock;

import com.example.jpalock.optimissticLock.entity.Member;
import com.example.jpalock.optimissticLock.repository.MemberRepository;
import com.example.jpalock.optimissticLock.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.fail;

@SpringBootTest
public class OptimissticLockTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void MemberInsert() {
        Member.builder().build();
        Member memberSave = memberRepository.save(Member.builder()
                        .version(0)
                        .name("77kkyu")
                        .build());
        assertThat(memberSave).isNotNull();
    }

    @Test
    public void MemberUpdate() {

        Optional<Member> member = memberRepository.findById(2L);

        member.ifPresent(selectedUser ->{ // 최초의 커밋만 인정
            selectedUser.setName("77kkyu1");
            memberRepository.save(selectedUser);
        });

        member.ifPresent(selectedUser ->{ // optimisticLock Exception
            selectedUser.setName("77kkyu2");
            memberRepository.save(selectedUser);
        });

        assertThat(member.get()).isNotNull();

    }

    @Test
    @Transactional
    public void 낙관적락예외() {
        // 영속 상태의 OptimisticMember
        Member member = entityManager.find(Member.class, 2L);

        // version은 version+1로 DB에 반영
        member.setName("77kkyu03");
        memberService.memberUpdate(member);

        try {
            member.setName("77kkyu04");
            memberService.memberUpdate(member); // 낙관적 락 오류 발생 (version+1로 되어 있음)
        } catch (Exception e) {
            e.printStackTrace(); // OptimisticLockException
            throw e;
        }

        fail("Optimistic Lock Exception!!!!!");
    }

}