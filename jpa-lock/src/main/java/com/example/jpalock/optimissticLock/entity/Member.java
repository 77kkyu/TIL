package com.example.jpalock.optimissticLock.entity;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@OptimisticLocking(type = OptimisticLockType.ALL) // 낙관적락
//@DynamicUpdate // 낙관적락
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long memberNo;

    private String name;

    @Version // 낙관적락
    private int version;

    @Builder
    public Member(Long memberNo, String name, int version) {
        this.memberNo = memberNo;
        this.name = name;
        this.version = version;
    }
}
