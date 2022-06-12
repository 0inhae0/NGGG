package com.example.NGGG.dto;

import com.example.NGGG.domain.ProductImg;
import lombok.Data;

//DTO for 상품세부조회(response), 상품리스트조회(response)
@Data
public class ProductImgDto {
    private String storeImgName; //저장된 이름
    private String productImgCode; //main, detail

    public ProductImgDto(ProductImg productImg) {
        storeImgName = productImg.getStoreImgName();
        productImgCode = productImg.getProductImgCode().toString();
    }
}