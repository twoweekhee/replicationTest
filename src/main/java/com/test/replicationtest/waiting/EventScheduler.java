package com.test.replicationtest.waiting;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableScheduling
@EnableAsync
public class EventScheduler {

    private final WaitingService waitingService;

    @Async
    @Scheduled(fixedRate = 3000)
    public void waitingEventScheduler() {
        if (waitingService.isEmpty()) {
            return;
        }
        waitingService.getIn();
    }
}
