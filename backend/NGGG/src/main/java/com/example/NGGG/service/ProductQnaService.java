package com.example.NGGG.service;

import com.example.NGGG.domain.Admin;
import com.example.NGGG.domain.Member;
import com.example.NGGG.domain.Product;
import com.example.NGGG.domain.ProductQna;
import com.example.NGGG.exception.WrongArgException;
import com.example.NGGG.repository.AdminRepository;
import com.example.NGGG.repository.MemberRepository;
import com.example.NGGG.repository.ProductQna.ProductQnaRepository;
import com.example.NGGG.repository.Product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductQnaService {

    private final ProductQnaRepository productQnaRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;

    /**
     * 상품문의 등록
     */
    @Transactional
    public int addQna(int productNo, int memberNo, String productQnaQue) {

        //엔티티 조회
        Product product = productRepository.findById(productNo)
                .orElseThrow(() -> new WrongArgException("Bad Product Request"));
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new WrongArgException("Bad Member Request"));

        //상품문의 생성
        ProductQna productQna = ProductQna.createProductQna(product, member);
        productQna.setProductQnaQue(productQnaQue);
        productQnaRepository.save(productQna);

        return productQna.getNo();

    }

    /**
     * 상품문의 답변
     */
    @Transactional
    public void ansQna(int adminNo, int productQnaNo, String productQnaAns) {

        //엔티티 조회
        Admin admin = adminRepository.findById(adminNo)
                .orElseThrow(() -> new WrongArgException("Bad Admin Request"));

        ProductQna productQna = productQnaRepository.findById(productQnaNo)
                .orElseThrow(() -> new WrongArgException("Bad ProductQna Request"));
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
