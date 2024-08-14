package com.test.replicationtest.global.config;

import com.test.replicationtest.jwt.JwtProvider;
import com.test.replicationtest.member.MemberRole;
import com.test.replicationtest.oauth.OAuth2FailureHandler;
import com.test.replicationtest.oauth.OAuth2SuccessHandler;
import com.test.replicationtest.oauth.Oauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final Oauth2UserService oauth2UserService;
    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, InMemoryClientRegistrationRepository clientRegistrationRepository) throws Exception {

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/oauth-login/admin").hasRole(MemberRole.ADMIN.name())
                        .requestMatchers("/oauth-login/info").authenticated()
                        .anyRequest().permitAll()
                );

        http
                .oauth2Login(oauth -> oauth
                        .authorizationEndpoint(p -> p.baseUri("/oauth2/authorization")
                            //.authorizationRequestRepository(cookieAuthorizationRequestRepository())
                            )
                        .redirectionEndpoint(p -> p.baseUri("/login/oauth2/code/*"))
                        .userInfoEndpoint(p -> p.userService(oauth2UserService))
                        .successHandler(oAuth2SuccessHandler)
                        .failureHandler(oAuth2FailureHandler)
                );

        // 로그인 설정
        http
                .formLogin(AbstractHttpConfigurer::disable);
        // 로그아웃 URL 설정
        http
                .logout((auth) -> auth
                        .logoutUrl("/logout")
                );
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable);

        return http
                .build();
    }

}