package com.example.NGGG.service;

import com.example.NGGG.domain.Category;
import com.example.NGGG.domain.Product;
import com.example.NGGG.dto.ProductDto;
import com.example.NGGG.dto.ProductListPageResponse;
import com.example.NGGG.dto.ProductRequest;
import com.example.NGGG.exception.NotFoundException;
import com.example.NGGG.exception.WrongArgException;
import com.example.NGGG.repository.CategoryRepository;
import com.example.NGGG.repository.Product.ProductQueryRepository;
import com.example.NGGG.repository.Product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductQueryRepository productQueryRepository;
    private final CategoryRepository categoryRepository;

    /**
     * 상품 등록
     */
    @Transactional
    public int addProduct(String productName, int productPrice, String productInfo, int productStocks, int categoryNo) {
        //엔티티 조회
        Category category = categoryRepository.findById(categoryNo)
                .orElseThrow(() -> new NotFoundException("Category Not Found"));

        //상품 생성
        Product product = Product.createProduct(productName, productPrice, productInfo, productStocks, category);
        productRepository.save(product);

        return product.getNo();
    }


    /**
     * 상품 리스트 조회 - 전체, 카테고리별
     */
    @Transactional
    public ProductListPageResponse getProductList(String categoryName, PageRequest pageRequest, int sort) {
        List<Product> productList;
        int totalCount; //총 상품 개수
        int totalPages; //총 페이지 수

        int size = pageRequest.getPageSize();
        Category category = categoryRepository.findByCategoryName(categoryName);

        if(category == null) {
            if(categoryName.equals("A")) { //전체
                Page<Product> page = productRepository.findAll(pageRequest);
                productList = page.getContent();
                totalCount = Math.toIntExact(page.getTotalElements());
                totalPages = page.getTotalPages();
            } else throw new WrongArgException("Wrong Category Input");
        } else { //스티커, 엽서, 키링, 머그컵, 그외
            int categoryNo = category.getNo();
            int offset = Math.toIntExact(pageRequest.getOffset());

            productList = productQueryRepository.findByCategoryOrdered(categoryNo, offset, size, sort);
            totalCount = Math.toIntExact(productQueryRepository.totalCount(categoryNo));
            totalPages = (totalCount%size==0)? totalCount/size : totalCount/size+1;
        }

        List<ProductDto> productDtoList = productList.stream()
                .map(p -> new ProductDto(p))
                .collect(Collectors.toList());

        return new ProductListPageResponse(productDtoList, totalCount, totalPages);
    }

    /**
     * 상품 리스트 조회 - 신상품
     */
    @Transactional
    public List<Product> getNewProductList() {
        List<Product> productList = productRepository.findTop10ByOrderByProductRegdateDesc();
        return productList;
    }

    /**
     * 상품 리스트 조회 - 베스트
     */
    @Transactional
    public List<Product> getBestProductList() {
        List<Product> productList = productRepository.findTop10ByOrderByProductLikeCntDesc();
        return productList;
    }

    /**
     * 상품 수정
     */
    @Transactional
    public void update(int productNo, ProductRequest request) {
        Product findProduct = productRepository.findById(productNo)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));
        Category category = categoryRepository.findById(request.getCategoryNo())
                .orElseThrow(() -> new NotFoundException("Category Not Found"));

        findProduct.setProductName(request.getProductName());
        findProduct.setProductPrice(request.getProductPrice());
        findProduct.setProductInfo(request.getProductInfo());
        findProduct.setProductStocks(request.getProductStocks());
        findProduct.setCategory(category);
    }


    /**
     * 상품 삭제
     */
    @Transactional
    public void deleteProduct(int productNo) {
        Product findProduct = productRepository.findById(productNo)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));
        //연관관계 끊기
        findProduct.getProductQnas().stream()
                        .forEach(q -> q.setProduct(null));
        findProduct.getProductReviews().stream()
                        .forEach(r -> r.setProduct(null));
        findProduct.getOrderProducts().stream()
                        .forEach(op -> op.setProduct(null));
        productRepository.deleteById(productNo);
    }

    /**
     * 상품 하나 조회
     */
    public Product findProduct(int productNo) {
        Product findProduct = productRepository.findById(productNo)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));
        return findProduct;
    }




}
