package com.example.NGGG.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

//DTO for 로그인(response)
@Data
@AllArgsConstructor
public class LoginAdminResponse {
    private String token;
    private int adminNo;
}
