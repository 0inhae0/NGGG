package com.example.NGGG.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "product_review")
@Getter
public class ProductReview {

    @Id @GeneratedValue
    private int product_review_no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_no")
    private Product product;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    private String product_review_content;

    private byte[] product_review_img;
}
