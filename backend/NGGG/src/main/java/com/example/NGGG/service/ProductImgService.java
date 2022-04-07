package com.example.NGGG.service;

import com.example.NGGG.common.ImgStore;
import com.example.NGGG.domain.ImgCode;
import com.example.NGGG.domain.Product;
import com.example.NGGG.domain.ProductImg;
import com.example.NGGG.dto.ProductRequest;
import com.example.NGGG.exception.NotFoundException;
import com.example.NGGG.repository.ProductImg.ProductImgQueryRepository;
import com.example.NGGG.repository.ProductImg.ProductImgRepository;
import com.example.NGGG.repository.Product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductImgService {

    private final ImgStore imgStore;
    private final ProductImgRepository productImgRepository;
    private final ProductImgQueryRepository productImgQueryRepository;
    private final ProductRepository productRepository;

    /**
     * 상품 이미지 등록 - 메인 이미지
     */
    public ProductImg addMainImg(MultipartFile multipartFile) throws IOException {
        ProductImg mainImg = imgStore.storeImg(multipartFile, ImgCode.MAIN);
        return mainImg;
    }

    /**
     * 상품 이미지 등록 - 상세 이미지
     */
    public List<ProductImg> addDetailImgs(List<MultipartFile> multipartFileList) throws IOException {
        List<ProductImg> detailImgs = imgStore.storeDetailImgs(multipartFileList);
        return detailImgs;
    }

    /**
     * 상품 이미지 등록
     */
    @Transactional
    public void addProductImgs(ProductRequest request, int productNo, MultipartFile main, List<MultipartFile> details) throws IOException { //main, details 추가됨

        Product product = productRepository.findById(productNo)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));

        //메인 이미지 저장
        //ProductImg mainImg = addMainImg(request.getMainImg());
        ProductImg mainImg = addMainImg(main);

        mainImg.setProduct(product);
        productImgRepository.save(mainImg);

        //상세 이미지 저장
        //List<ProductImg> detailImgs = addDetailImgs(request.getDetailImgs());
        List<ProductImg> detailImgs = addDetailImgs(details);

        detailImgs.stream()
                .forEach(img -> img.setProduct(product));
        detailImgs.stream()
                .forEach(img -> productImgRepository.save(img));

    }

    /**
     * 상품 이미지 삭제
     */
    @Transactional
    public void deleteProductImgs(int productNo) {
        productImgRepository.deleteByProductNo(productNo);
    }


}
