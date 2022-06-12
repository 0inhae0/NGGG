package com.example.NGGG.controller;

import com.example.NGGG.common.ImgStore;
import com.example.NGGG.domain.*;
import com.example.NGGG.dto.*;
import com.example.NGGG.exception.WrongArgException;
import com.example.NGGG.service.ProductImgService;
import com.example.NGGG.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;
    private final ProductImgService productImgService;
    private final ImgStore imgStore;

    /**
     * 상품 등록
     */
    @PostMapping("/product/add")
    public ResponseEntity<AddProductResponse> addProduct(
            @RequestPart(value="value") @Valid ProductRequest request,
            @RequestPart(value="main") MultipartFile main,
            @RequestPart(value="details") List<MultipartFile> details) //추가됨
            throws IOException {

        //상품 엔티티 생성
        int productNo = productService.addProduct(request.getProductName(), request.getProductPrice(), request.getProductInfo(), request.getProductStocks(), request.getCategoryNo());

        //상품 이미지 등록
        //productImgService.addProductImgs(request, productNo);
        productImgService.addProductImgs(request, productNo, main, details);

        return ResponseEntity.ok(new AddProductResponse(productNo));
    }

    /**
     * 상품 메인 이미지 로드
     * src="/main/{storeImgName}"
     */
    @ResponseBody
    @GetMapping("/main/{img_name}")
    public Resource processMainImg(@PathVariable("img_name") String storeImgName) throws MalformedURLException {
        //url로부터 resource를 가져옴
        return new UrlResource("file:" + imgStore.createPath(storeImgName, ImgCode.MAIN));
    }

    /**
     * 상품 상세 이미지 로드
     * src="/detail/{storeImgName}"
     */
    @ResponseBody
    @GetMapping("/detail/{img_name}")
    public Resource processDetailImg(@PathVariable("img_name") String storeImgName) throws MalformedURLException {
        return new UrlResource("file:" + imgStore.createPath(storeImgName, ImgCode.DETAIL));
    }

    /**
     * 상품 세부 조회
     */
    @GetMapping("/product/{product_no}")
    public ResponseEntity<ViewProductResponse> viewProduct(@PathVariable("product_no") int productNo) {
        Product product = productService.findProduct(productNo);
        return ResponseEntity.ok(new ViewProductResponse(product));
    }

    /**
     * 상품 리스트 조회 - 전체,카테고리별
     */
    @PostMapping("/product/list")
    public ResponseEntity<ProductListPageResponse> getProductList(@RequestBody @Valid ProductListRequest request) {
        //정렬조건
        PageRequest pageRequest;
        int sort = request.getSort();
        switch(sort) {
            case 0: //인기순
                pageRequest = PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Direction.DESC, "productLikeCnt"));
                break;
            case 1: //신상품순
                pageRequest = PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Direction.DESC, "productRegdate"));
                break;
            case 2: //높은가격순
                pageRequest = PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Direction.DESC, "productPrice"));
                break;
            case 3: //낮은가격순
                pageRequest = PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Direction.ASC, "productPrice"));
                break;
            default: //정렬x
                throw new WrongArgException("Wrong Sort Input");
        }

        ProductListPageResponse result = productService.getProductList(request.getCategoryName(), pageRequest, sort);
        return ResponseEntity.ok(result);
    }

    /**
     * 상품 리스트 조회 - 신상품 (상위 10개)
     */
    @GetMapping("/product/new")
    public ResponseEntity<ProductListResponse> getNewProductList() {
        List<Product> productList = productService.getNewProductList();
        List<ProductDto> productDtoList = productList.stream()
                .map(p -> new ProductDto(p))
                .collect(Collectors.toList());
        int count = productDtoList.size();
        return ResponseEntity.ok(new ProductListResponse(productDtoList, count));
    }

    /**
     * 상품 리스트 조회 - 베스트 (상위 10개)
     */
    @GetMapping("/product/best")
    public ResponseEntity<ProductListResponse> getBestProductList() {
        List<Product> productList = productService.getBestProductList();
        List<ProductDto> productDtoList = productList.stream()
                .map(p -> new ProductDto(p))
                .collect(Collectors.toList());
        int count = productDtoList.size();
        return ResponseEntity.ok(new ProductListResponse(productDtoList, count));
    }

    /**
     * 상품 수정
     */
    @GetMapping("/product/{product_no}/update")
    public ResponseEntity<UpdateProductResponse> updateProductForm (@PathVariable("product_no") int productNo) {
        Product product = productService.findProduct(productNo);
        return ResponseEntity.ok(new UpdateProductResponse(product));
    }

    @PostMapping("/product/{product_no}/update")
    public ResponseEntity updateProduct(@PathVariable("product_no") int productNo,
                                        @RequestPart(value="value") @Valid ProductRequest request,
                                        @RequestPart(value="main") MultipartFile main,
                                        @RequestPart(value="details") List<MultipartFile> details) throws IOException{
        //상품 정보 수정(텍스트)
        productService.update(productNo, request);
        //기존 상품 이미지 삭제
        productImgService.deleteProductImgs(productNo);
        //상품 이미지 수정
        productImgService.addProductImgs(request, productNo, main, details);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 상품 삭제
     */
    @DeleteMapping("/product/{product_no}/delete")
    public ResponseEntity deleteProduct(@PathVariable("product_no") int productNo) {
        productService.deleteProduct(productNo);
        return new ResponseEntity(HttpStatus.OK);
    }


    //DTO for 상품 리스트 조회-전체(request)
    @Data
    static class ProductListRequest {
        @NotNull
        private int page; //몇번째 페이지(0부터 시작)
        @NotNull
        private int size; //페이지당 데이터 개수
        @NotBlank
        private String categoryName; //S:스티커, P:엽서, K:키링, M:머그컵, O:그외, (A:전체)
        @NotNull
        private int sort; //0:인기순, 1:신상품순, 2:높은가격순, 3:낮은가격순
    }

    //DTO for 상품 리스트 조회- 신상품, 베스트(response)
    @Data
    static class ProductListResponse {
        private List<ProductDto> productDtoList;
        private int count;

        public ProductListResponse(List<ProductDto> productDtoList, int count) {
            this.productDtoList = productDtoList;
            this.count = count;
        }
    }

    //DTO for 상품등록(response)
    @Data
    @AllArgsConstructor
    static class AddProductResponse {
        private int productNo;
    }

    //DTO for 상품세부조회(response), 상품세부수정(response)
    @Data
    static class ViewProductResponse {
        private String productName;
        private int productPrice;
        private String productInfo;
        private LocalDate productRegdate;
        private int productStocks;
        private int productLikeCnt;
        private List<ProductImgDto> productImgs;

        public ViewProductResponse(Product product) {
            productName = product.getProductName();
            productPrice = product.getProductPrice();
            productInfo = product.getProductInfo();
            productRegdate = product.getProductRegdate();
            productStocks = product.getProductStocks();
            productLikeCnt = product.getProductLikeCnt();
            productImgs = product.getProductImgs().stream()
                    .map(img -> new ProductImgDto(img))
                    .collect(Collectors.toList());
        }
    }

    @Data
    static class UpdateProductResponse {
        private String productName;
        private int productPrice;
        private String productInfo;
        private int productStocks;
        private List<ProductImgDto> productImgs;

        public UpdateProductResponse(Product product) {
            productName = product.getProductName();
            productPrice = product.getProductPrice();
            productInfo = product.getProductInfo();
            productStocks = product.getProductStocks();
            productImgs = product.getProductImgs().stream()
                    .map(img -> new ProductImgDto(img))
                    .collect(Collectors.toList());
        }
    }

}
