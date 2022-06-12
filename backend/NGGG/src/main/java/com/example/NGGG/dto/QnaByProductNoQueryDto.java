package com.example.NGGG.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class QnaByProductNoQueryDto {

    private int productQnaNo;

    //que
    private String memberNickname;
    private String productQnaQue;

    //ans
    private String adminName;
    private String productQnaAns;

    //time
    private LocalDateTime qnaRegdate;
}
