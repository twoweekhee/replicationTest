package com.test.replicationtest.oauth;

import com.test.replicationtest.global.RedisUtil;
import com.test.replicationtest.jwt.JWTUtil;
import com.test.replicationtest.member.Member;
import com.test.replicationtest.member.MemberDto;
import com.test.replicationtest.member.MemberService;
import com.test.replicationtest.oauth.info.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Oauth2Controller {

    private final RedisUtil redisUtil;
    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @GetMapping("/oauth-join/user")
    public ResponseEntity<MemberDto> getSessionUser() {

        OAuth2UserInfo oAuth2UserInfo = (OAuth2UserInfo) redisUtil.getData("oauth2UserInfo");

        return ResponseEntity.ok(MemberDto.builder()
                .name(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
                .build());
    }

    @PostMapping("/oauth-join/user")
    public ResponseEntity<Member> saveUser(@RequestBody MemberDto memberDto) {

        Member member = memberService.saveMember(memberDto);

        return ResponseEntity.ok(member);
    }

}
