package com.example.jpalock.pessimisticLock.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pmember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pmemberNo;

    private String name;

    @Builder
    public Pmember(Long pmemberNo, String name) {
        this.pmemberNo = pmemberNo;
        this.name = name;
    }
}
