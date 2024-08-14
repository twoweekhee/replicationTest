package com.test.replicationtest.waiting;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WaitingController {

    private final WaitingService waitingService;
    private final EventScheduler eventScheduler;

    @GetMapping("/check/waiting")
    public ResponseEntity<Boolean> isEmpty() {
        log.info("result : {}", waitingService.isEmpty());
        return ResponseEntity.ok(waitingService.isEmpty());
    }

    @GetMapping("/waiting")
    public ResponseEntity<String> addWaitingQueue() {
        return ResponseEntity.ok(waitingService.addWaitingQueue());
    }

    @GetMapping("/order")
    public ResponseEntity<Long> getOrder() {

        return ResponseEntity.ok(waitingService.getOrder());
    }
}
