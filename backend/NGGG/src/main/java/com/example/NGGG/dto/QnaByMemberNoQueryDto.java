package com.example.NGGG.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class QnaByMemberNoQueryDto {

    //product
    private int productNo;
    private String productName;

    //product img
    private ProductImgDto mainImg;

    //que
    private String productQnaQue;

    //ans
    private String adminName;
    private String productQnaAns;

    //time
    private LocalDate qnaRegdate;

    public QnaByMemberNoQueryDto(int productNo, String productName, String productQnaQue, String adminName, String productQnaAns, LocalDate qnaRegdate) {
        this.productNo = productNo;
        this.productName = productName;
        this.productQnaQue = productQnaQue;
        this.adminName = adminName;
        this.productQnaAns = productQnaAns;
        this.qnaRegdate = qnaRegdate;
    }
}
