package com.example.jpa_persistence_context.repository;

import com.example.jpa_persistence_context.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
