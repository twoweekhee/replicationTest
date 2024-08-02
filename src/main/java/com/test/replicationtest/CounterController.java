package com.test.replicationtest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CounterController {

    @Autowired
    private ServletWebServerApplicationContext webServerAppCtxt;

    @GetMapping
    public String count() {
        int port = webServerAppCtxt.getWebServer().getPort();
        log.info("port: {}", port);
        return "FirstPage, running on port: " + port;
    }
}

