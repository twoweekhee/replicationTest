package com.test.replicationtest.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberDto {
    private String name;
    private String email;
    private String role;
    private String userName;

    @Builder
    public MemberDto( String name, String email, String role, String userName) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.userName = userName;
    }
}