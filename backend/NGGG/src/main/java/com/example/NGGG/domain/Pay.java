package com.example.NGGG.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Pay {

    @Id @GeneratedValue
    @Column(name = "pay_no")
    private int no;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "order_no")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "paycode_no")
    private Paycode paycode;

    private LocalDateTime payDate;
}
