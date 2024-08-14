package com.test.replicationtest.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class KafkaProducerController {

    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/kafka")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        this.kafkaProducerService.sendMessage(message);
        return ResponseEntity.ok(message);
    }
}
