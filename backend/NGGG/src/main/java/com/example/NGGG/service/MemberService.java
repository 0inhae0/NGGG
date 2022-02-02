package com.example.NGGG.service;

import com.example.NGGG.domain.Member;
import com.example.NGGG.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    public int join(Member member) {
        //validateDuplicateMember(member); //중복회원 검증
        memberRepository.save(member);
        return member.getNo();
    }

    //중복 ID 검증
    /*
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findById(member.getMemberId());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }*/


}
