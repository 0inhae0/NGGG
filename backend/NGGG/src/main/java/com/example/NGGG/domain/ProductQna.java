package com.example.NGGG.domain;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "product_qna")
@Getter
public class ProductQna {

    @Id @GeneratedValue
    @Column(name = "product_qna_no")
    private int no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_no")
    private Product product;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    private String productQnaQuestion;

    private String productQnaAnswer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "admin_no")
    private Admin admin;
}
