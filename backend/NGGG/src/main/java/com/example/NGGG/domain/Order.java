package com.example.NGGG.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
    private int order_no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    private int cart_amount; //fk?????

    private String order_name;

    private String order_tel;

    private String order_address;

    private String order_comment;

    private LocalDateTime order_date;

    private int order_state;

    @OneToOne(mappedBy = "order", fetch = LAZY)
    private Pay pay;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts = new ArrayList<>();
}
