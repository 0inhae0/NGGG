package com.example.NGGG.controller;

import com.example.NGGG.domain.ProductQna;
import com.example.NGGG.dto.QnaByMemberNoQueryDto;
import com.example.NGGG.dto.QnaByProductNoQueryDto;
import com.example.NGGG.dto.QnaListPageResponse;
import com.example.NGGG.exception.ForbiddenException;
import com.example.NGGG.exception.NotFoundException;
import com.example.NGGG.repository.ProductQna.ProductQnaQueryRepository;
import com.example.NGGG.repository.ProductQna.ProductQnaRepository;
import com.example.NGGG.service.ProductQnaService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.lang.Integer.parseInt;

@RestController
@RequiredArgsConstructor
public class ProductQnaApiController {

    private final ProductQnaService productQnaService;
    private final ProductQnaQueryRepository productQnaQueryRepository;
    private final ProductQnaRepository productQnaRepository;

    /**
     * 상품문의 등록
     */
    @PostMapping("/qna/add")
    public ResponseEntity<AddQnaResponse> addQna(@RequestBody @Valid AddQnaRequest request, Authentication authentication) {
        int productQnaNo = productQnaService.addQna(request.getProductNo(), parseInt(authentication.getName()), request.getProductQnaQue());
        return ResponseEntity.ok(new AddQnaResponse(productQnaNo));
    }

    /**
     * 상품문의 답변
     */
    @PostMapping("/qna/answer")
    public ResponseEntity ansQna(@RequestBody @Valid AnsQnaRequest request, Authentication authentication) {
        productQnaService.ansQna(parseInt(authentication.getName()), request.getProductQnaNo(), request.getProductQnaAns());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 상품문의 조회 - 상품별
     */
    @GetMapping("/product/{product_no}/qna")
    public ResponseEntity<QnaListPageResponse> getQnaByProduct(@PathVariable("product_no") int productNo, @RequestBody @Valid QnaListRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Direction.DESC, "qnaRegdate"));
        QnaListPageResponse result = productQnaService.getQnaByProduct(productNo, pageRequest);
        return ResponseEntity.ok(result);
    }

    /**
     * 상품문의 조회 - 회원별
     */
    @GetMapping("/member/{member_no}/qna")
    public ResponseEntity<QnaListPageResponse> getQnaByMember(@PathVariable("member_no") int memberNo,
                                                                      Authentication authentication,
                                                                      @RequestBody @Valid QnaListRequest request) {
        //자신의 상품문의가 아니면
        if(parseInt(authentication.getName()) != memberNo) {
            throw new ForbiddenException("Access Denied");
        }
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Direction.DESC, "qnaRegdate"));
        QnaListPageResponse result = productQnaService.getQnaByMember(memberNo, pageRequest);
        return ResponseEntity.ok(result);
    }

    /**
     * 상품문의 삭제
     */
    @DeleteMapping("/qna/{product_qna_no}/delete")
    public ResponseEntity deleteQna(@PathVariable("product_qna_no") int productQnaNo, Authentication authentication) {
        ProductQna productQna = productQnaRepository.findById(productQnaNo)
                .orElseThrow(() -> new NotFoundException("ProductQna Not Found"));

        //자신의 상품문의가 아니면
        if(parseInt(authentication.getName()) != productQna.getMember().getNo()) {
            throw new ForbiddenException("Access Denied");
        }
        productQnaService.deleteQna(productQnaNo);
        return new ResponseEntity(HttpStatus.OK);
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
    
    //DTO for 상품문의 조회(request)
    @Data
    static class QnaListRequest {
        @NotNull
        private int page; //몇번째 페이지(0부터 시작)
        @NotNull
        private int size; //페이지당 데이터 개수
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
