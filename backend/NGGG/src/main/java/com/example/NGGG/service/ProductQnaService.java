package com.example.NGGG.service;

import com.example.NGGG.domain.*;
import com.example.NGGG.dto.ProductImgDto;
import com.example.NGGG.dto.QnaByMemberNoQueryDto;
import com.example.NGGG.dto.QnaByProductNoQueryDto;
import com.example.NGGG.dto.QnaListPageResponse;
import com.example.NGGG.exception.NotFoundException;
import com.example.NGGG.repository.AdminRepository;
import com.example.NGGG.repository.MemberRepository;
import com.example.NGGG.repository.ProductImg.ProductImgQueryRepository;
import com.example.NGGG.repository.ProductQna.ProductQnaQueryRepository;
import com.example.NGGG.repository.ProductQna.ProductQnaRepository;
import com.example.NGGG.repository.Product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductQnaService {

    private final ProductQnaRepository productQnaRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;
    private final ProductQnaQueryRepository productQnaQueryRepository;
    private final ProductImgQueryRepository productImgQueryRepository;

    /**
     * 상품문의 등록
     */
    @Transactional
    public int addQna(int productNo, int memberNo, String productQnaQue) {

        //엔티티 조회
        Product product = productRepository.findById(productNo)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new NotFoundException("Member Not Found"));

        //상품문의 생성
        ProductQna productQna = ProductQna.createProductQna(product, member);
        productQna.setProductQnaQue(productQnaQue);
        productQnaRepository.save(productQna);

        return productQna.getNo();

    }

    /**
     * 상품문의 조회 - 상품별
     */
    @Transactional
    public QnaListPageResponse getQnaByProduct(int productNo, PageRequest pageRequest) {
        if(!productRepository.existsById(productNo)) {
            throw new NotFoundException("Product Not Found");
        }
        int offset = Math.toIntExact(pageRequest.getOffset());
        int size = pageRequest.getPageSize();

        List<QnaByProductNoQueryDto> qnaDtoList = productQnaQueryRepository.findByProductNo(productNo, offset, size);
        int totalCount = Math.toIntExact(productQnaQueryRepository.totalCountByProductNo(productNo));
        int totalPages = (totalCount%size==0)? totalCount/size : totalCount/size+1;

        return new QnaListPageResponse(qnaDtoList, totalCount, totalPages);
    }

    /**
     * 상품문의 조회 - 회원별
     */
    @Transactional
    public QnaListPageResponse getQnaByMember(int memberNo, PageRequest pageRequest) {
        if(!memberRepository.existsById(memberNo)) {
            throw new NotFoundException("Member Not Found");
        }
        int offset = Math.toIntExact(pageRequest.getOffset());
        int size = pageRequest.getPageSize();

        //memberNo를 통해 상품문의 리스트 가져오기
        List<QnaByMemberNoQueryDto> qnaDtoList = productQnaQueryRepository.findByMemberNo(memberNo, offset, size);

        //productNo를 통해 mainImg 가져오기
        qnaDtoList.stream()
                .forEach(dto -> {
                    int productNo = dto.getProductNo();
                    ProductImgDto mainImg = productImgQueryRepository.findByProductNo(productNo).stream()
                            .filter(i -> i.getProductImgCode().equals(ImgCode.MAIN))
                            .map(i -> new ProductImgDto(i))
                            .collect(Collectors.toList())
                            .get(0);
                    dto.setMainImg(mainImg);
                });
        int totalCount = Math.toIntExact(productQnaQueryRepository.totalCountByMemberNo(memberNo));
        int totalPages = (totalCount%size==0)? totalCount/size : totalCount/size+1;
        return new QnaListPageResponse(qnaDtoList, totalCount, totalPages);
    }

    /**
     * 상품문의 답변
     */
    @Transactional
    public void ansQna(int adminNo, int productQnaNo, String productQnaAns) {
        //엔티티 조회
        Admin admin = adminRepository.findById(adminNo)
                .orElseThrow(() -> new NotFoundException("Admin Not Found"));
        ProductQna productQna = productQnaRepository.findById(productQnaNo)
                .orElseThrow(() -> new NotFoundException("ProductQna Not Found"));

        productQna.setAdmin(admin);
        productQna.setProductQnaAns(productQnaAns);
    }

    /**
     * 상품문의 삭제
     */
    @Transactional
    public void deleteQna(int productQnaNo) {
        productQnaRepository.deleteById(productQnaNo);
    }

}
