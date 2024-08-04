package com.test.replicationtest.global.config;

import com.test.replicationtest.member.MemberRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/oauth-login/admin").hasRole(MemberRole.ADMIN.name())
                        .requestMatchers("/oauth-login/info").authenticated()
                        .anyRequest().permitAll()
                );

        http
                .oauth2Login((auth) -> auth.loginPage("/oauth-login/login")
                        .defaultSuccessUrl("http://localhost:3000")
                        .failureUrl("/oauth-login/login")
                        .permitAll());

        // 로그인 설정
        http
                .formLogin(AbstractHttpConfigurer::disable);
        // 로그아웃 URL 설정
        http
                .logout((auth) -> auth
                        .logoutUrl("/logout")
                );
        http
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}