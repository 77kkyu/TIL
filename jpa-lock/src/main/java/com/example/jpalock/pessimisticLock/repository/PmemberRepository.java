package com.example.jpalock.pessimisticLock.repository;

import com.example.jpalock.pessimisticLock.entity.Pmember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PmemberRepository extends JpaRepository<Pmember, Long> {
}
