package com.example.NGGG.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UpdateMemberRequest {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String memberPwd; //해싱 -> 수정

    @NotBlank(message = "이름을 입력해주세요.")
    private String memberName;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String memberNickname;

    @NotBlank(message = "주소를 입력해주세요.")
    private String memberAddress;

}