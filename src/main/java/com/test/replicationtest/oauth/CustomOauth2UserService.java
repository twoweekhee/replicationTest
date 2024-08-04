package com.test.replicationtest.oauth;

import com.test.replicationtest.member.Member;
import com.test.replicationtest.member.MemberRepository;
import com.test.replicationtest.member.MemberRole;
import com.test.replicationtest.oauth.info.GoogleUserInfo;
import com.test.replicationtest.oauth.info.KaKaoUserInfo;
import com.test.replicationtest.oauth.info.NaverUserInfo;
import com.test.replicationtest.oauth.info.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : {}",oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = null;

        // 뒤에 진행할 다른 소셜 서비스 로그인을 위해 구분 => 구글
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

        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String loginId = provider + "_" + providerId;
        String name = oAuth2UserInfo.getName();

        Member findMember = memberRepository.findByLoginId(loginId);
        Member member;

        if (findMember == null) {
            member = Member.builder()
                    .loginId(loginId)
                    .name(name)
                    .provider(provider)
                    .providerId(providerId)
                    .role(MemberRole.USER)
                    .build();
            memberRepository.save(member);
        } else{
            member = findMember;
        }

        return new CustomOauth2UserDetails(member, oAuth2User.getAttributes());
    }
}