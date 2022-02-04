package com.example.NGGG.domain;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "member_product")
@Getter
public class MemberProduct {

    @Id @GeneratedValue
    @Column(name = "member_product_no")
    private int no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_no")
    private Product product;
}
