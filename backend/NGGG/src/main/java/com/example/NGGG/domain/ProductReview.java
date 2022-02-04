package com.example.NGGG.domain;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "product_review")
@Getter
public class ProductReview {

    @Id @GeneratedValue
    private int productReviewNo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_no")
    private Product product;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    private String productReviewContent;

    private byte[] productReviewImg;
}
