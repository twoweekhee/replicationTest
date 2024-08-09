package com.test.replicationtest.oauth;

import com.test.replicationtest.jwt.JwtProvider;
import com.test.replicationtest.member.Member;
import com.test.replicationtest.member.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private static final String URI = "http://localhost:3000/";
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response
            , Authentication authentication) throws IOException {

        // accessToken, refreshToken 발급
        String accessToken = jwtProvider.generateAccessToken(authentication);
        jwtProvider.generateRefreshToken(authentication);

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = authToken.getPrincipal();

        String provider = authToken.getAuthorizedClientRegistrationId();

        // 이메일로 사용자 조회
        String email = "";

        if(provider.equals("google")){
            email = oAuth2User.getAttribute("email");
        }else if (provider.equals("naver")) {
            Map<String, Object> attributes = oAuth2User.getAttribute("response");
            email = attributes.get("email").toString();
        }else if (provider.equals("kakao")) {
            log.info("getAttributes Kakao : {}" , oAuth2User.getAttributes());
            Map<String, Object> attributes = oAuth2User.getAttribute("kakao_account");
            email = attributes.get("email").toString();
        }

        Member member = memberRepository.findByEmail(email);

        String redirectUrl;

        if (member == null) {
            redirectUrl = UriComponentsBuilder.fromUriString(URI)
                    .path("oauth-login/join")
                    .build().toUriString();
        } else {
            redirectUrl = UriComponentsBuilder.fromUriString(URI)
                    .path(member.getRole() + "/oauth-login/success")
                    .build().toUriString();
        }

        response.sendRedirect(redirectUrl);
    }

}
