package com.test.replicationtest.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class Oauth2Controller {

    @GetMapping("/oauth-login")
    public String index() {
        log.info("Success");
        return "Success";
    }
}
