package com.example.NGGG.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//DTO for 상품등록(request), 상품수정(request)
@Data
public class ProductRequest {

    @NotBlank
    private String productName;

    @NotNull
    private int productPrice;

    @NotBlank
    private String productInfo;

    @NotNull
    private int productStocks;

    @NotNull
    private int categoryNo;

    //private MultipartFile mainImg;

    //private List<MultipartFile> detailImgs;
}
