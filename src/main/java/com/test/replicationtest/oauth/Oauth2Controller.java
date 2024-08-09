package com.test.replicationtest.oauth;

import com.test.replicationtest.global.RedisUtil;
import com.test.replicationtest.member.*;
import com.test.replicationtest.oauth.info.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Oauth2Controller {

    private final RedisUtil redisUtil;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/oauth-login")
    public String index() {
        log.info("Success");
        return "Success";
    }

    @GetMapping("/oauth-join/user")
    public MemberDto getSessionUser() {

        OAuth2UserInfo oAuth2UserInfo = (OAuth2UserInfo) redisUtil.getData("oauth2UserInfo");

        return MemberDto.builder()
                .name(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .provider(oAuth2UserInfo.getProvider())
                .providerId(oAuth2UserInfo.getProviderId())
                .build();
    }

    @PostMapping("/oauth-join/user")
    public Member saveUser(@RequestBody MemberDto memberDto) {

        Member member = memberService.saveMember(memberDto);

        return member;
    }

}
