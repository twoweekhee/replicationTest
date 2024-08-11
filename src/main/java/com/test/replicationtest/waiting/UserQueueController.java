//package com.test.replicationtest.waiting;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Set;
//
//@RestController("/api")
//@RequiredArgsConstructor
//public class UserQueueController {
//
//    private final UserQueueService userQueueService;
//
//    @GetMapping("/next-in-queue")
//    public String getNextInQueue() {
//        return "Next in queue: " + userQueueService.getNextInQueue();
//    }
//
//    @GetMapping("/all-in-queue")
//    public Set<Object> getAllInQueue() {
//        return userQueueService.getAllInQueue();
//    }
//
//    @GetMapping("/remove-from-queue")
//    public String removeFromQueue(@RequestParam String userId) {
//        userQueueService.removeFromQueue(userId);
//        return "Removed from queue";
//    }
//
//    @GetMapping("/waiting")
//    public boolean isWaiting() {
//        return userQueueService.isEmpty();
//    }
//
//    @PostMapping("/waiting")
//    public boolean addWaiting(@RequestParam String userId, @RequestParam double score) {
//        return userQueueService.isEmpty();
//    }
//}
