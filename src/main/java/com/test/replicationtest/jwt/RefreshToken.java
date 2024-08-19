package com.test.replicationtest.jwt;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;


@Getter
@RedisHash(value = "token", timeToLive = 60 * 60 * 24 * 7)
@RequiredArgsConstructor
public class RefreshToken implements Serializable {

    @Id
    private String id;

    @Indexed
    private String accessToken;
    private String refreshToken;

    @Builder
    public RefreshToken(String id, String accessToken, String refreshToken) {
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
