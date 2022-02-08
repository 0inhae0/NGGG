package com.example.NGGG.controller;

import com.example.NGGG.domain.Member;
import com.example.NGGG.dto.LoginMemberResponse;
import com.example.NGGG.dto.UpdateMemberRequest;
import com.example.NGGG.service.MemberService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;


import static com.example.NGGG.controller.MemberApiController.CreateMemberRequest.createMember;
import static java.util.Collections.singletonList;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 로그인
     */
    @PostMapping("/member/login")
    public LoginMemberResponse loginMember(@RequestBody LoginMemberRequest request) {
        LoginMemberResponse response = memberService.login(request.getMemberId(), request.getMemberPwd());
        return response;
    }

    /**
     * 회원 가입
    */
    @PostMapping("/member/signup")
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request) {

        Member member = createMember(request);

        if (memberService.checkIdDuplicate(request.getMemberId())
            || memberService.checkEmailDuplicate(request.getMemberEmail())
            || memberService.checkNicknameDuplicate(request.getMemberNickname())) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        } else {
            int no = memberService.join(member);
            return new CreateMemberResponse(no);
        }

    }

    /**
     * 아이디 중복 확인
     */
    @PostMapping("/member/idcheck")
    public ResponseEntity<Boolean> checkIdDuplicate(@RequestBody String id) {
        //중복인 경우 true, 아니면 false
        return ResponseEntity.ok(memberService.checkIdDuplicate(id));
    }

    /**
     * 닉네임 중복 확인
     */
    @PostMapping("/member/namecheck")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@RequestBody String nickname) {
        return ResponseEntity.ok(memberService.checkNicknameDuplicate(nickname));
    }


    /**
     * 이메일 중복 확인
     */
    @PostMapping("/member/emailcheck")
    public ResponseEntity<Boolean> checkEmailDuplicate(@RequestBody String email) {
        //중복인 경우 true, 아니면 false
        return ResponseEntity.ok(memberService.checkEmailDuplicate(email));
    }


    /**
     * 개인정보 수정
     */
    @PostMapping("/member/update/{member_no}")
    public MemberResponse updateMember(
            @PathVariable("member_no") int no,
            @RequestBody @Valid UpdateMemberRequest request) {

        memberService.update(no, request);
        Member findMember = memberService.findOne(no);
        return new MemberResponse(findMember);
    }


    /**
     * 개인정보 조회
     */
    @GetMapping("/member/{member_no}")
    public MemberResponse viewMember(@PathVariable("member_no") int no) {
        Member findMember = memberService.findOne(no);
        return new MemberResponse(findMember);
    }

    //DTO for 로그인(request)
    @Data
    static class LoginMemberRequest {

        @NotBlank
        private String memberId;

        @NotBlank
        private String memberPwd;
    }


    //DTO for 회원가입(response)
    @Data
    static class CreateMemberResponse {
        private int memberNo;

        public CreateMemberResponse(int no) {
            this.memberNo = no;
        }
    }

    //DTO for 회원가입(request)
    @Data
    static class CreateMemberRequest {

        @NotBlank(message = "아이디를 입력해주세요.")
        @Size(min = 6, message = "6자 이상의 아이디를 입력해주세요.")
        private String memberId;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String memberPwd;

        @NotBlank(message = "이름을 입력해주세요.")
        private String memberName;

        @NotBlank(message = "닉네임을 입력해주세요.")
        private String memberNickname;

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "올바른 이메일 주소를 입력해주세요.")
        private String memberEmail;

        @NotBlank(message = "휴대전화 번호를 입력해주세요.")
        @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대전화 번호를 입력해주세요.")
        private String memberTel;

        @NotBlank(message = "주소를 입력해주세요.")
        private String memberAddress;

        public static Member createMember(CreateMemberRequest request) {

            Member member = new Member(request.getMemberId(), request.getMemberPwd(), request.getMemberName(), request.getMemberNickname(), request.getMemberTel(), request.getMemberEmail(), request.getMemberAddress(), singletonList("ROLE_MEMBER"));
            return member;

        }

    }

    //DTO for 개인정보 수정(response), 개인정보 조회(response)
    @Getter
    static class MemberResponse {

        private String memberId;

        private String memberName;

        private String memberNickname;

        private String memberEmail;

        private String memberTel;

        private String memberAddress;

        public MemberResponse(Member member) {
            this.memberId = member.getMemberId();
            this.memberName = member.getMemberName();
            this.memberNickname = member.getMemberNickname();
            this.memberEmail = member.getMemberEmail();
            this.memberTel = member.getMemberTel();
            this.memberAddress = member.getMemberAddress();
        }

    }

}
