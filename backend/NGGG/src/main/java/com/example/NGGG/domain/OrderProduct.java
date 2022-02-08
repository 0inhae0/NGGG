package com.example.NGGG.domain;

import lombok.Getter;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

}
