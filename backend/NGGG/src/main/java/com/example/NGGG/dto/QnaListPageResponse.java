package com.example.NGGG.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QnaListPageResponse {
    private List<?> qnaDtoList; //QnaByProductNoQueryDto, QnaByMemberNoQueryDto
    private int totalCount; //해당 상품의 총 상품문의 개수
    private int totalPages; //총 페이지 수
}
