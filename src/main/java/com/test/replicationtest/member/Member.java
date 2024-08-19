package com.test.replicationtest.member;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private String userName;

    @Builder
    public Member(String email, String userName, String name, MemberRole role) {
        this.email = email;
        this.userName = userName;
        this.name = name;
        this.role = role;
    }
}
