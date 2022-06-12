package com.example.NGGG.service;

import com.example.NGGG.domain.Member;
import com.example.NGGG.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Collections.singletonList;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void Member_SignUp() throws Exception {

        //given
        Member member = new Member("wus2363", "rhekgus123!", "고다현", "땅링", "01072409621", "wus2363@gmail.com", "왕산로", singletonList("ROLE_MEMBER"));

        //when
        int no = memberService.join(member);

        //then
        Assertions.assertEquals(member, memberRepository.findByNo(no));
    }

}
