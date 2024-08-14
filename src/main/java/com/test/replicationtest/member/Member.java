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

    private String loginId;
    private String password;
    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private String provider;
    private String providerId;

    @Builder
    public Member(String email, String loginId, String password, String name, MemberRole role, String provider, String providerId) {
        this.email = email;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
}
