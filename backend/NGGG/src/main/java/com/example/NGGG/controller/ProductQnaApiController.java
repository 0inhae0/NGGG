package com.example.NGGG.controller;

import com.example.NGGG.domain.ProductQna;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequiredArgsConstructor
public class ProductQnaApiController {

    /**
     * 상품문의 등록
     */
    /*
    @PostMapping("/qna/add")
    public CreateQnaResponse addQna(@RequestBody @Valid CreateQnaRequest request) {
        ProductQna productQna = new
    }*/



    //DTO for 상품문의 등록
    @Data
    static class CreateQnaRequest {

        @NotBlank
        private int productNo;

        @NotBlank
        private String productQnaQue;
/*
        public static ProductQna createProductQna(CreateQnaRequest request) {
            ProductQna productQna = new ProductQna(request.getProductNo(), request.getProductQnaQue());
        }*/
    }

    //DTO for 상품문의 등록
    @Data
    static class CreateQnaResponse {
        private int productQnaNo;

        public CreateQnaResponse(int productQnaNo) {
            this.productQnaNo = productQnaNo;
        }
    }


}
