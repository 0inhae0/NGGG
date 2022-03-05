package com.example.NGGG.dto;

import lombok.Data;

import java.util.List;

//DTO for 상품 리스트 조회- 전체(카테고리별)
@Data
public class ProductListPageResponse {
    private List<ProductDto> productDtoList;
    private int totalCount;
    private int totalPages;

    public ProductListPageResponse(List<ProductDto> productDtoList, int totalCount, int totalPages) {
        this.productDtoList = productDtoList;
        this.totalCount = totalCount;
        this.totalPages = totalPages;
    }
}
