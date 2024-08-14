package com.test.replicationtest.oauth;

import com.test.replicationtest.global.RedisUtil;
import com.test.replicationtest.jwt.JwtProvider;
import com.test.replicationtest.member.Member;
import com.test.replicationtest.member.MemberDto;
import com.test.replicationtest.member.MemberService;
import com.test.replicationtest.oauth.info.OAuth2UserInfo;
import com.test.replicationtest.oauth.info.OauthResponseDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Oauth2Controller {

    private final RedisUtil redisUtil;
    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final HttpSession httpSession;

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

    @GetMapping("/check")
    public ResponseEntity<OauthResponseDto> checkLoginStatus() {

        OAuth2UserInfo oAuth2UserInfo;

        if (redisUtil.getData("oauth2UserInfo") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    OauthResponseDto.builder()
                            .isLoggedIn(false)
                            .build());
        } else {
            oAuth2UserInfo = (OAuth2UserInfo) redisUtil.getData("oauth2UserInfo");
        }

        String accessToken = (String) redisUtil.getData(oAuth2UserInfo.getEmail());

        if (accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        }

        if (jwtProvider.validateToken(accessToken)) {
            return ResponseEntity.ok(OauthResponseDto.builder()
                    .isLoggedIn(true)
                    .role( jwtProvider.getRole(accessToken)).build());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    OauthResponseDto.builder()
                    .isLoggedIn(false)
                    .build());
        }
    }

}
