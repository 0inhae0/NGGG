package com.example.NGGG.domain;

import com.example.NGGG.exception.NotEnoughStocksException;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Product {

    @Id @GeneratedValue
    @Column(name = "product_no")
    private int no;

    @Setter
    private String productName;

    @Setter
    private int productPrice;

    @Setter
    private String productInfo;

    @CreationTimestamp
    private LocalDate productRegdate;

    private int productStocks;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_no")
    private Category category;

    @ColumnDefault("0")
    private int productLikeCnt; //default 0

    @OneToMany(mappedBy = "product")
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductQna> productQnas = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<ProductReview> productReviews = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImg> productImgs = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<MemberProduct> memberProducts = new ArrayList<>();

    //==연관관계 편의 메소드==//
    public void setCategory(Category category) {
        this.category = category;
        category.getProducts().add(this);
    }

    //==생성 메서드==//
    public static Product createProduct(String productName, int productPrice, String productInfo, int productStocks, Category category) {
        Product product = new Product();
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setProductInfo(productInfo);
        product.addStocks(productStocks);
        product.setCategory(category);
        return product;
    }

    //==비즈니스 로직==//
    /**
     * 재고 증가
     */
    public void addStocks(int quantity) {
        this.productStocks += quantity;
    }

    /**
     * 재고 감소
     */
    public void removeStocks(int quantity) {
        int restStocks = this.productStocks;
        if(restStocks < 0) {
            throw new NotEnoughStocksException("Need More Stocks");
        }
        this.productStocks -= quantity;
    }

}
