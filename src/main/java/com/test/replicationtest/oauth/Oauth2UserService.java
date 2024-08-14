package com.test.replicationtest.oauth;

import com.test.replicationtest.global.RedisUtil;
import com.test.replicationtest.jwt.JwtProvider;
import com.test.replicationtest.jwt.JwtRedis;
import com.test.replicationtest.member.Member;
import com.test.replicationtest.member.MemberService;
import com.test.replicationtest.oauth.info.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class Oauth2UserService extends DefaultOAuth2UserService {

    private final RedisUtil redisUtil;
    private final MemberService memberService;
    private final JwtProvider jwtProvider;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId();
        log.info("provider : {}",provider);

        OAuth2UserInfo oAuth2UserInfo = null;

        //다른 소셜 서비스 로그인을 위해 구분
        if(provider.equals("google")){
            log.info("구글 로그인");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }else if (provider.equals("naver")) {
            log.info("네이버 로그인");
            oAuth2UserInfo = new NaverUserInfo(oAuth2User.getAttributes());
        }else if (provider.equals("kakao")) {
            log.info("카카오 로그인");
            oAuth2UserInfo = new KaKaoUserInfo(oAuth2User.getAttributes());
        }

        String email = oAuth2UserInfo.getEmail();
        Member findMember = memberService.findByEmail(email);

        if (findMember == null) {
            redisUtil.setData("oauth2UserInfo", oAuth2UserInfo, Duration.ofMinutes(2));
            log.info("oAuth2UserInfo : {}", oAuth2UserInfo);
            return oAuth2User;
        } else {
            return new Oauth2UserDetails(findMember, oAuth2UserInfo.getAttributes());
        }

    }

    public JwtRedis saveToken(Authentication authentication, String email) {
        String accessToken = jwtProvider.generateAccessToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);


        JwtRedis jwt = JwtRedis.builder()
                .id(email)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        redisUtil.setData(email, accessToken, Duration.ofMinutes(2));

        return jwt;
    }

}