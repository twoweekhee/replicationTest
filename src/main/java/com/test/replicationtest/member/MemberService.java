package com.test.replicationtest.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String getTest() {
        memberRepository.save(new Member());
        return "test";
    }

    @Transactional(readOnly = true)
    public String getTestReadOnly(){
        memberRepository.findById(1L);
        return "test";
    }

    @Transactional
    public Member saveMember(MemberDto userRegistrationRequest) {
        Member member = Member.builder()
                .name(userRegistrationRequest.getName())
                .email(userRegistrationRequest.getEmail())
                .provider(userRegistrationRequest.getProvider())
                .providerId(userRegistrationRequest.getProviderId())
                .role(validateRole(userRegistrationRequest.getRole()))
                .build();
        memberRepository.save(member);

        return member;
    }

    private MemberRole validateRole(String role) {
        if (role.equals("admin")) {
            return MemberRole.ADMIN;
        } else if (role.equals("user")) {
            return MemberRole.USER;
        } else {
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }
}
