package com.test.replicationtest.oauth;

import com.test.replicationtest.global.RedisUtil;
import com.test.replicationtest.jwt.JWTUtil;
import com.test.replicationtest.jwt.RefreshToken;
import com.test.replicationtest.jwt.RefreshTokenRepository;
import com.test.replicationtest.member.Member;
import com.test.replicationtest.member.MemberDto;
import com.test.replicationtest.member.MemberService;
import com.test.replicationtest.oauth.info.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Oauth2Controller {

    private final RedisUtil redisUtil;
    private final MemberService memberService;
    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

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

    @PostMapping("/token/refresh")
    public ResponseEntity<String> refresh(@RequestHeader("Authorization") final String accessToken) {

        // 액세스 토큰으로 Refresh 토큰 객체를 조회
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccessToken(accessToken);

        // RefreshToken이 존재하고 유효하다면 실행
        if (refreshToken.isPresent() && !jwtUtil.isExpired(refreshToken.get().getRefreshToken())) {
            // RefreshToken 객체를 꺼내온다.
            RefreshToken resultToken = refreshToken.get();
            // 권한과 아이디를 추출해 새로운 액세스토큰을 만든다.
            String newAccessToken = jwtUtil.createAccessToken(resultToken.getId(), jwtUtil.getRole(resultToken.getRefreshToken()));
            // 액세스 토큰의 값을 수정해준다.
            resultToken.updateAccessToken(newAccessToken);
            refreshTokenRepository.save(resultToken);
            // 새로운 액세스 토큰을 반환해준다.
            return ResponseEntity.ok(newAccessToken);
        }

        return ResponseEntity.badRequest().body(null);
    }

}
