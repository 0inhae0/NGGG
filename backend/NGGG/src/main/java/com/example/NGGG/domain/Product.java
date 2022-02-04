package com.example.NGGG.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Product {

    @Id @GeneratedValue
    @Column(name = "product_no")
    private int no;

    private String productName;

    private int productPrice;

    private String productInfo;

    private LocalDateTime productRegdate;

    private int productStocks;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_no")
    private Category category;

    private int productLikeCnt;

    @OneToMany(mappedBy = "product")
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductQna> productQnas = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductReview> productReviews = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductImg> productImgs = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<MemberProduct> memberProducts = new ArrayList<>();
}
