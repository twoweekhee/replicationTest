package com.test.replicationtest.member;

import com.test.replicationtest.global.data.Replica;
import com.test.replicationtest.global.data.Source;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final EntityManager entityManager;


    @Source
    public Member saveMember(MemberDto memberDto) {
        Member member = Member.builder()
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .userName(memberDto.getUserName())
                .role(validateRole(memberDto.getRole()))
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

    @Replica
    public Member findByEmail(String email) {
        entityManager.clear();

        return memberRepository.findByEmail(email);
    }

    @Replica
    public Member findByUserName(String userName) {
        return memberRepository.findByUserName(userName);
    }
}
