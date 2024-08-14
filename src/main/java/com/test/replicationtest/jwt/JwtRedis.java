package com.test.replicationtest.jwt;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Entity
@Getter
@RedisHash(value = "token", timeToLive = 60 * 60 * 24 * 7)
@RequiredArgsConstructor
public class JwtRedis {

    @Id
    private String id;

    @Indexed
    private String accessToken;
    private String refreshToken;

    @Builder
    public JwtRedis(String id, String accessToken, String refreshToken) {
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
