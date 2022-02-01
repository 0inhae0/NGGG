package com.example.NGGG.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Product {

    @Id @GeneratedValue
    private int product_no;

    private String product_name;

    private int product_price;

    private String product_info;

    private LocalDateTime product_regdate;

    private int product_stocks;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_no")
    private Category category;

    private int product_like_cnt;

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
