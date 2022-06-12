package com.example.NGGG.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "product_img")
@Getter
@Setter
public class ProductImg {

    @Id @GeneratedValue
    @Column(name = "product_img_no")
    private int no;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_no")
    private Product product;

    private String originImgName; //원본 이름
    private String storeImgName; //저장된 이름

    @Enumerated(EnumType.STRING)
    private ImgCode productImgCode;

    //==연관관계 편의 메소드==//
    public void setProduct(Product product) {
        this.product = product;
        product.getProductImgs().add(this);
    }

    //==생성 메소드==//
    public static ProductImg createProductImg(String originImgName, String storeImgName, ImgCode imgCode) {
        ProductImg productImg = new ProductImg();
        productImg.setOriginImgName(originImgName);
        productImg.setStoreImgName(storeImgName);
        productImg.setProductImgCode(imgCode);
        return productImg;
    }

}
