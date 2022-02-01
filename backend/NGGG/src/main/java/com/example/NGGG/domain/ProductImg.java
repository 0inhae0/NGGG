package com.example.NGGG.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "product_img")
@Getter
public class ProductImg {

    @Id @GeneratedValue
    private int product_img_no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_no")
    private Product product;

    @NotEmpty
    private byte[] product_img_name;

    private int product_img_code;

}
