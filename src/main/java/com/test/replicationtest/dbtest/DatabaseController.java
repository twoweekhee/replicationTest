package com.test.replicationtest.dbtest;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DatabaseController {

    private final DatabaseService databaseService;

    @GetMapping("/source")
    public ResponseEntity<String>  source() {
        log.info(databaseService.addData());
        return ResponseEntity.ok(databaseService.addData());
    }

    @GetMapping("/replica")
    public String replica() {
        log.info(databaseService.getData());
        return databaseService.getData();
    }
}
