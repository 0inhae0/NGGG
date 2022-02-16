package com.example.NGGG.service;

import com.example.NGGG.repository.ProductQnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductQnaService {

    private final ProductQnaRepository productQnaRepository;

    /**
     * 상품문의 등록
     */
}
