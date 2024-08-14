package com.test.replicationtest;

import com.test.replicationtest.global.data.DataSourceContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HomeComtroller {

    private final ServletWebServerApplicationContext webServerAppCtxt;

    @GetMapping("/port")
    public int getPort() {
        int port = webServerAppCtxt.getWebServer().getPort();
        log.info("Current port: " + port);
        return port;
    }

    @GetMapping("/db")
    public String getDataBase() {

        return DataSourceContextHolder.getDataSourceType();
    }

}
