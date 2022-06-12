package com.example.NGGG.domain;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "order_product")
@Getter
public class OrderProduct {

    @Id @GeneratedValue
    @Column(name = "order_product_no")
    private int no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_no")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_no")
    private Product product;

    private int productCnt;

    //==연관관계 편의 메소드==//
    public void setProduct(Product product) {
        if(product == null) { //Product을 삭제할 때 연관관계 끊기 위함
            this.product = null;
        } else {
            this.product = product;
            product.getOrderProducts().add(this);
        }
    }

}
