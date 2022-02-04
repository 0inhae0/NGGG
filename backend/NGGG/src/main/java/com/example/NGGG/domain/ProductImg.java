package com.example.NGGG.domain;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "product_img")
@Getter
public class ProductImg {

    @Id @GeneratedValue
    @Column(name = "product_img_no")
    private int no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_no")
    private Product product;

    private byte[] productImgName;

    private int productImgCode;

}
