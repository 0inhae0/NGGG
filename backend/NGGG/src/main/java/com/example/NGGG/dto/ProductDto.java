package com.example.NGGG.dto;

import com.example.NGGG.domain.ImgCode;
import com.example.NGGG.domain.Product;
import lombok.Data;

import java.util.stream.Collectors;

@Data
public class ProductDto {
    private int productNo;
    private String productName;
    private int productPrice;
    private ProductImgDto mainImg;
    private int productLikeCnt;

    public ProductDto(Product product) {
        productNo = product.getNo();
        productName = product.getProductName();
        productPrice = product.getProductPrice();
        mainImg = product.getProductImgs().stream()
                .filter(i -> i.getProductImgCode().equals(ImgCode.MAIN))
                .map(i -> new ProductImgDto(i))
                .collect(Collectors.toList())
                .get(0);
        productLikeCnt = product.getProductLikeCnt();
    }
}