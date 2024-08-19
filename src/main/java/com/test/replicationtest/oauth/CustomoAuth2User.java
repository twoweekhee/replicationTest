package com.test.replicationtest.oauth;

import com.test.replicationtest.member.MemberDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomoAuth2User implements OAuth2User {

    @Getter
    private final MemberDto memberDto;

    public CustomoAuth2User(MemberDto memberDto) {
        this.memberDto = memberDto;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return memberDto.getRole();
            }
        });
        return authorities;
    }

    @Override
    public String getName() {
        return memberDto.getName();
    }

    public String getUserName() {
        return memberDto.getUserName();
    }

}
