package com.example.NGGG.controller;

import com.example.NGGG.domain.Member;
import com.example.NGGG.repository.MemberRepository;
import com.example.NGGG.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    /**
     * 회원 가입
    */
    @PostMapping("/member/signup")
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request) {
        
        //닉네임 중복체크
        //이메일 중복체크
        //id 중복체크
        Member member = new Member();
        member.setMemberId(request.getMemberId());
        member.setMemberPwd(request.getMemberPwd());
        member.setMemberName(request.getMemberName());
        member.setMemberNickname(request.getMemberNickname());
        member.setMemberTel(request.getMemberTel());
        member.setMemberAddress(request.getMemberAddress());
        member.setMemberEmail(request.getMemberEmail());
        member.setMemberJoindate(request.getMemberJoindate());

        int no = memberService.join(member);
        return new CreateMemberResponse(no);

    }

    /**
     * 닉네임 중복 확인
     */
    @PostMapping("/member/namecheck")
    public void validateDuplicateNickname(String nickname) {
        List<Member> findMembers = memberRepository.findByNickname(nickname);
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }


    /**
     * 아이디 중복 확인
     */
    @PostMapping("/member/idcheck")
    public void validateDuplicateId(String id) {
        List<Member> findMembers = memberRepository.findById(id);
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

    /**
     * 이메일 중복 확인
     */
    @PostMapping("/member/emailcheck")
    public void validateDuplicateEmail(String email) {
        List<Member> findMembers = memberRepository.findByEmail(email);
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }

    //DTO for 회원가입(response)
    @Data
    static class CreateMemberResponse {
        private int no;

        public CreateMemberResponse(int no) {
            this.no = no;
        }
    }

    //DTO for 회원가입(request)
    @Data
    static class CreateMemberRequest {

        @NotBlank(message = "아이디를 입력해주세요.")
        @Size(min = 6, message = "6자 이상의 아이디를 입력해주세요.")
        private String memberId;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        private String memberPwd;

        @NotBlank
        private String memberPwdConfirm;

        @NotBlank
        private String memberName;

        @NotBlank
        private String memberNickname;

        @NotBlank
        private String memberEmail;

        @NotBlank
        private String memberTel;

        @NotBlank
        private String memberAddress;

        //수정해야 함! 어떻게 값을 넣지?
        //@NotEmpty
        private LocalDateTime memberJoindate;

    }
}
