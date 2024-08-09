package com.test.replicationtest;

import com.test.replicationtest.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TestController {

    @Autowired
    MemberService memberService;

    @Test
    public void test(){
        System.out.println(memberService.getTest());
    }

    @Test
    public void testReadOnly(){
        System.out.println(memberService.getTestReadOnly());
    }


}
