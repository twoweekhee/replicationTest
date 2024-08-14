package com.test.replicationtest.waiting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
public class WaitingService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ZSetOperations<String, Object> zSetOps;

    private static final long FIRST_ELEMENT = 0;
    private static final long LAST_ELEMENT = -1; // 마지막까지를 의미하는 Redis ZSet의 기본값
    private static final long PUBLISH_SIZE = 30; // 한 번에 처리할 사용자 수
    private static final long LAST_INDEX = 1;

    public WaitingService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.zSetOps = redisTemplate.opsForZSet();  // RedisTemplate을 통해 ZSetOperations를 가져옴
    }

    public String addWaitingQueue(){
        final String thread = Thread.currentThread().getName();
        final long now = System.currentTimeMillis();

        redisTemplate.opsForZSet().add("waiting", thread, (int) now);
        log.info("대기열에 추가 - {} ({}초)", thread, now);
        return thread;
    }

    public Long getOrder(){
        final long start = FIRST_ELEMENT;
        final long end = LAST_ELEMENT;
        final String thread = Thread.currentThread().getName();
        Long rank = redisTemplate.opsForZSet().rank("waiting", thread);
        if (rank == null) {
            return 0L;
        } else {
            return rank;
        }
    }

    public void getIn() {
        final long start = FIRST_ELEMENT;
        final long end = PUBLISH_SIZE;

        Set<Object> queue = redisTemplate.opsForZSet().range("waiting", start, end);
        for (Object people : queue) {
            log.info("참여하였습니다. {}", people);
            redisTemplate.opsForZSet().remove("waiting", people);
        }
    }

    public boolean isEmpty() {
        if (zSetOps.size("waiting") == 0) {
            return true;
        }
        return false;
    }
}
