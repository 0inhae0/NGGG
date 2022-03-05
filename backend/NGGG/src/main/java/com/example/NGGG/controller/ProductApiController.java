package com.example.NGGG.controller;

import com.example.NGGG.common.ImgStore;
import com.example.NGGG.domain.*;
import com.example.NGGG.dto.AddProductRequest;
import com.example.NGGG.dto.ProductDto;
import com.example.NGGG.dto.ProductImgDto;
import com.example.NGGG.dto.ProductListPageResponse;
import com.example.NGGG.exception.NotFoundException;
import com.example.NGGG.repository.Product.ProductQueryRepository;
import com.example.NGGG.repository.Product.ProductRepository;
import com.example.NGGG.service.ProductImgService;
import com.example.NGGG.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    private final ProductQueryRepository productQueryRepository;
    private final ProductRepository productRepository;
    private final ImgStore imgStore;

    /**
     * 상품 등록
     */
    @PostMapping("/product/add")
    public ResponseEntity<?> addProduct(
            @RequestPart(value="value") @Valid AddProductRequest request,
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
    public GetProductResponse getProduct(@PathVariable("product_no") int productNo) {
        Product product = productRepository.findById(productNo)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));
        return new GetProductResponse(product);
    }

    //모두 페이징 고려해야할듯...

    /**
     * 상품 리스트 조회 - 전체(카테고리별)
     */
    @PostMapping("/product/list")
    public ProductListPageResponse getProductList(@RequestBody @Valid ProductListRequest request) {
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
                pageRequest = PageRequest.of(request.getPage(), request.getSize());
                break;
        }

        return productService.getProductList(request.getCategoryName(), pageRequest, sort);
    }

    /**
     * 상품 리스트 조회 - 신상품 (상위 10개)
     */
    @GetMapping("/product/new")
    public ProductListResponse getNewProductList() {
        List<Product> productList = productService.getNewProductList();
        List<ProductDto> productDtoList = productList.stream()
                .map(p -> new ProductDto(p))
                .collect(Collectors.toList());
        int count = productDtoList.size();
        return new ProductListResponse(productDtoList, count);
    }

    /**
     * 상품 리스트 조회 - 베스트 (상위 10개)
     */
    @GetMapping("/product/best")
    public ProductListResponse getBestProductList() {
        List<Product> productList = productService.getBestProductList();
        List<ProductDto> productDtoList = productList.stream()
                .map(p -> new ProductDto(p))
                .collect(Collectors.toList());
        int count = productDtoList.size();
        return new ProductListResponse(productDtoList, count);
    }

    /**
     * 상품 수정
     */


    /**
     * 상품 삭제
     */
    @DeleteMapping("/product/delete/{product_no}")
    public void deleteProduct(@PathVariable("product_no") int productNo) {
        Product product = productRepository.findById(productNo)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));
        productService.deleteProduct(productNo);
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
        private int totalCount;

        public ProductListResponse(List<ProductDto> productDtoList, int totalCount) {
            this.productDtoList = productDtoList;
            this.totalCount = totalCount;
        }
    }

    //DTO for 상품등록(response)
    @Data
    @AllArgsConstructor
    static class AddProductResponse {
        private int productNo;
    }

    //DTO for 상품세부조회(response)
    @Data
    static class GetProductResponse {
        private String productName;
        private int productPrice;
        private String productInfo;
        private LocalDate productRegdate;
        private int productStocks;
        private int productLikeCnt;
        private List<ProductImgDto> productImgs;

        public GetProductResponse(Product product) {
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

}
