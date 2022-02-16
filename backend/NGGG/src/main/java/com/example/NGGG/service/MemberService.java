package com.example.NGGG.service;

import com.example.NGGG.common.security.JwtTokenProvider;
import com.example.NGGG.common.security.UserType;
import com.example.NGGG.domain.Member;
import com.example.NGGG.dto.LoginMemberResponse;
import com.example.NGGG.dto.UpdateMemberRequest;
import com.example.NGGG.exception.NotFoundException;
import com.example.NGGG.exception.WrongArgException;
import com.example.NGGG.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 로그인
     */
    public LoginMemberResponse login(String id, String pwd) {
        Member findMember = memberRepository.findByMemberId(id)
                .orElseThrow(() -> new NotFoundException("ID Not Found"));
        if(!passwordEncoder.matches(pwd, findMember.getMemberPwd())) {
            //비밀번호가 일치하지 않음
            throw new WrongArgException("Wrong Password");
        }
        String token = jwtTokenProvider.createToken(UserType.MEMBER.toString(), findMember.getUsername(), findMember.getRoles());
        int memberNo = findMember.getNo();
        return new LoginMemberResponse(token, memberNo);
    }

    /**
     * 회원가입
     */
    public int join(Member member) {
        //비밀번호 암호화
        String encodedMemberPwd = passwordEncoder.encode(member.getMemberPwd());
        member.setMemberPwd(encodedMemberPwd);

        memberRepository.save(member);
        return member.getNo();
    }

    /**
     * 아이디 중복 확인
     */
    public boolean checkIdDuplicate(String id) {
        return memberRepository.existsByMemberId(id);
    }

    /**
     * 닉네임 중복 확인
     */
    public boolean checkNicknameDuplicate(String nickname) {
        return memberRepository.existsByMemberNickname(nickname);
    }


    /**
     * 이메일 중복 확인
     */
    public boolean checkEmailDuplicate(String email) {
        return memberRepository.existsByMemberEmail(email);
    }

    /**
     * 회원 수정
     */
    @Transactional
    public void update(int no, UpdateMemberRequest request) {
        Member findMember = memberRepository.findById(no)
                .orElseThrow(() -> new NotFoundException("Member Not Found"));
        //비밀번호 암호화
        String encodedMemberPwd = passwordEncoder.encode(request.getMemberPwd());
        request.setMemberPwd(encodedMemberPwd);

        findMember.updateMember(request);
    }

    /**
     * 회원 한명 조회
     */
    public Member findOne(int no){
        return memberRepository.findByNo(no);
    }



}
