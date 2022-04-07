package com.example.NGGG.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;

import java.time.LocalDate;

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

    @CreationTimestamp
    private LocalDate reviewRegdate; //나중에 LocalDateTime으로 수정

    //==연관관계 편의 메소드==//
    public void setProduct(Product product) {
        if(product == null) {
            this.product = null;
        } else {
            this.product = product;
            product.getProductReviews().add(this);
        }
    }
}
