package com.test.replicationtest.member;

public enum MemberRole {
    ADMIN("admin"), USER("user");

    private String name;

    private MemberRole(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
