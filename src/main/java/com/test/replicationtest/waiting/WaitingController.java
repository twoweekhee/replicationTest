package com.test.replicationtest.waiting;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WaitingController {

    private final WaitingService waitingService;
    private final EventScheduler eventScheduler;

    @GetMapping("/check/waiting")
    public boolean isEmpty() {
        log.info("result : {}", waitingService.isEmpty());
        return waitingService.isEmpty();
    }

    @GetMapping("/waiting")
    public Long addWaitingQueue() {
        waitingService.addWaitingQueue();
        return waitingService.getOrder();
    }
}
