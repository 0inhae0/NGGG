package com.example.NGGG.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
//ddl-auto:create 옵션 에러나서 변경
@Table(name = "orders")
@Getter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_no")
    private int no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    private int orderAmount;

    private String orderName;

    private String orderTel;

    private String orderAddress;

    private String orderComment;

    private LocalDateTime orderDate;

    private int orderState;

    @OneToOne(mappedBy = "order", fetch = LAZY)
    private Pay pay;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts = new ArrayList<>();
}
