package com.example.NGGG.controller;

import com.example.NGGG.domain.ImgCode;
import com.example.NGGG.domain.ProductQna;
import com.example.NGGG.dto.ProductImgDto;
import com.example.NGGG.dto.QnaByMemberNoQueryDto;
import com.example.NGGG.dto.QnaByProductNoQueryDto;
import com.example.NGGG.exception.ForbiddenException;
import com.example.NGGG.exception.NotFoundException;
import com.example.NGGG.repository.ProductImg.ProductImgQueryRepository;
import com.example.NGGG.repository.ProductQna.ProductQnaQueryRepository;
import com.example.NGGG.repository.ProductQna.ProductQnaRepository;
import com.example.NGGG.service.ProductQnaService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@RestController
@RequiredArgsConstructor
public class ProductQnaApiController {

    private final ProductQnaService productQnaService;
    private final ProductQnaQueryRepository productQnaQueryRepository;
    private final ProductQnaRepository productQnaRepository;
    private final ProductImgQueryRepository productImgQueryRepository;

    /**
     * 상품문의 등록 - token 안넣어도 403 안뜸(개인정보 조회는 뜸)- ResponseEntity로 반환하면 안뜨는듯?
     */
    @PostMapping("/qna/add")
    public ResponseEntity<?> addQna(@RequestBody @Valid AddQnaRequest request, Authentication authentication) {
        int productQnaNo = productQnaService.addQna(request.getProductNo(), parseInt(authentication.getName()), request.getProductQnaQue());
        return ResponseEntity.ok(new AddQnaResponse(productQnaNo));
    }

    /**
     * 상품문의 답변
     */
    @PostMapping("/qna/answer")
    public void ansQna(@RequestBody @Valid AnsQnaRequest request, Authentication authentication) {
        productQnaService.ansQna(parseInt(authentication.getName()), request.getProductQnaNo(), request.getProductQnaAns());
    }

    /**
     * 상품문의 조회 - 상품별
     */
    @GetMapping("/product/qna/{product_no}")
    public ResponseEntity<?> getQnaByProduct(@PathVariable("product_no") int productNo) {
        List<QnaByProductNoQueryDto> result = productQnaQueryRepository.findByProductNo(productNo);
        return ResponseEntity.ok(result);
    }

    /**
     * 상품문의 조회 - 회원별
     */
    //service로 따로 빼는 것도 ㄱㅊ을듯
    @GetMapping("/member/qna/{member_no}")
    public ResponseEntity<?> getQnaByMember(@PathVariable("member_no") int memberNo, Authentication authentication) {

        //자신의 상품문의가 아니면
        if(parseInt(authentication.getName()) != memberNo) {
            throw new ForbiddenException("Access Denied");
        }
        //memberNo를 통해 상품문의 리스트 가져오기
        List<QnaByMemberNoQueryDto> result = productQnaQueryRepository.findByMemberNo(memberNo);

        //productNo를 통해 mainImg 가져오기
        result.stream()
                .forEach(dto -> {
                    int productNo = dto.getProductNo();
                    ProductImgDto mainImg = productImgQueryRepository.findByProductNo(productNo).stream()
                            .filter(i -> i.getProductImgCode().equals(ImgCode.MAIN))
                            .map(i -> new ProductImgDto(i))
                            .collect(Collectors.toList())
                            .get(0);
                    dto.setMainImg(mainImg);
                });
        return ResponseEntity.ok(result);
    }

    /**
     * 상품문의 삭제
     */
    @DeleteMapping("/qna/delete/{product_qna_no}")
    public void deleteQna(@PathVariable("product_qna_no") int productQnaNo, Authentication authentication) {

        ProductQna productQna = productQnaRepository.findById(productQnaNo)
                .orElseThrow(() -> new NotFoundException("ProductQna Not Found"));

        //자신의 상품문의가 아니면
        if(parseInt(authentication.getName()) != productQna.getMember().getNo()) {
            throw new ForbiddenException("Access Denied");
        }
        productQnaService.deleteQna(productQnaNo);
    }


    //DTO for 상품문의 등록(request)
    @Data
    static class AddQnaRequest {

        @NotNull
        private int productNo;

        @NotBlank
        private String productQnaQue;

    }

    //DTO for 상품문의 등록(response)
    @Data
    @AllArgsConstructor
    static class AddQnaResponse {
        private int productQnaNo;
    }

    //DTO for 상품문의 답변(request)
    @Data
    static class AnsQnaRequest {

        @NotNull
        private int productQnaNo;

        @NotBlank
        private String productQnaAns;

    }

}
