package com.example.NGGG.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

//DTO for 상품등록(request)
@Data
public class AddProductRequest {

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
