package com.example.NGGG.controller;

import com.example.NGGG.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 회원 가입 API
    */
    /*
    @PostMapping("/")
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request) {

    }*/
}
