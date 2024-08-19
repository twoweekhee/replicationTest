package com.test.replicationtest.jwt;

import com.test.replicationtest.member.MemberDto;
import com.test.replicationtest.oauth.CustomoAuth2User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = null;

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            log.info(cookie.getName() + " : " + cookie.getValue());
            if (cookie.getName().equals("Authorization")) {
                authorization = cookie.getValue();
            }
        }

        if (authorization == null) {
            log.info("token null");
            filterChain.doFilter(request, response);

            return;
        }

        String token = authorization;

        if (jwtUtil.isExpired(token)) {
            log.info("token expired");
            filterChain.doFilter(request, response);
            return;
        }

        String userName = jwtUtil.getUserName(token);
        String role = jwtUtil.getRole(token);

        CustomoAuth2User customoAuth2User = new CustomoAuth2User(MemberDto.builder()
                .role(role)
                .userName(userName)
                .build());

        Authentication authToken = new UsernamePasswordAuthenticationToken(customoAuth2User, null, customoAuth2User.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
