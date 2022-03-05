package com.example.NGGG.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class QnaByProductNoQueryDto {

    private int productQnaNo;

    //que
    private String memberNickname;
    private String productQnaQue;

    //ans
    private String adminName;
    private String productQnaAns;

    //time
    private LocalDate qnaRegdate;

    public QnaByProductNoQueryDto(int productQnaNo, String memberNickname, String productQnaQue, String adminName, String productQnaAns, LocalDate qnaRegdate) {
        this.productQnaNo = productQnaNo;
        this.memberNickname = memberNickname;
        this.productQnaQue = productQnaQue;
        this.adminName = adminName;
        this.productQnaAns = productQnaAns;
        this.qnaRegdate = qnaRegdate;
    }

}
