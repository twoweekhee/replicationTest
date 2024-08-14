package com.test.replicationtest.oauth.info;


import lombok.Builder;
import lombok.Getter;

@Getter
public class OauthResponseDto {

    private boolean isLoggedIn;
    private String role;

    @Builder
    public OauthResponseDto(boolean isLoggedIn, String role) {
        this.isLoggedIn = isLoggedIn;
        this.role = role;
    }
}
