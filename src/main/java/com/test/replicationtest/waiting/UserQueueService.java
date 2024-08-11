//package com.test.replicationtest.waiting;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ZSetOperations;
//import org.springframework.stereotype.Service;
//
//import java.util.Set;
//
//@Service
//@RequiredArgsConstructor
//public class UserQueueService {
//
//    private final RedisTemplate<String, Object> redisTemplate;
//    private final ZSetOperations<String, Object> zSetOps;
//
//    // 티켓팅 대기열에 사용자 추가
//    public void addToQueue(String userId, double priorityScore) {
//        zSetOps.add("ticketingQueue", userId, priorityScore);
//    }
//
//    // 대기열에서 특정 사용자 제거
//    public void removeFromQueue(String userId) {
//        zSetOps.remove("ticketingQueue", userId);
//    }
//
//    // 대기열에서 첫 번째 사용자 가져오기
//    public String getNextInQueue() {
//        Set<Object> range = zSetOps.range("ticketingQueue", 0, 0);
//        if (range != null && !range.isEmpty()) {
//            return (String) range.iterator().next();
//        }
//        return null;
//    }
//
//    // 전체 대기열 조회
//    public Set<Object> getAllInQueue() {
//        return zSetOps.range("ticketingQueue", 0, -1);
//    }
//
//    public boolean isEmpty() {
//        return zSetOps.size("ticketingQueue") == 0;
//    }
//
//
//}
