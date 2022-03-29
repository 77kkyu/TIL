package com.example.jpalock.optimissticLock.repository;

import com.example.jpalock.optimissticLock.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
