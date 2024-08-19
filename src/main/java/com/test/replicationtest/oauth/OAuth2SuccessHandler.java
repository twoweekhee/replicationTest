package com.test.replicationtest.oauth;

import com.test.replicationtest.jwt.JWTUtil;
import com.test.replicationtest.member.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private static final String URI = "http://localhost:3000/";
    private final MemberService memberService;
    private final Oauth2UserService oauth2UserService;

    @Value("${jwt.expiredMS}")
    private Long EXPIRED_MS;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response
            , Authentication authentication) throws IOException {

        CustomoAuth2User oAuth2User = (CustomoAuth2User) authentication.getPrincipal();

        String userName = oAuth2User.getUserName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(userName, role, EXPIRED_MS);

       response.addCookie(createCookie("Authorization", token));
       response.sendRedirect(URI);
    }

    private Cookie createCookie(String name, String value) {

        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(EXPIRED_MS.intValue());
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

}
