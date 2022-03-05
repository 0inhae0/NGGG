package com.example.NGGG.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "product_qna")
@Getter
@NoArgsConstructor
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "admin_no")
    private Admin admin;

    @Setter
    private String productQnaQue;

    @Setter
    private String productQnaAns;

    @CreationTimestamp
    private LocalDate qnaRegdate;

    //==연관관계 편의 메서드==//
    public void setProduct(Product product) {
        this.product = product;
        product.getProductQnas().add(this);
    }

    public void setMember(Member member) {
        this.member = member;
        member.getProductQnas().add(this);
    }

    public void setAdmin(Admin admin) {
        if(this.admin != null) {
            this.admin.getProductQnas().remove(this);
        }
        this.admin = admin;
        admin.getProductQnas().add(this);
    }

    //==생성 메서드==//
    public static ProductQna createProductQna(Product product, Member member) {
        ProductQna productQna = new ProductQna();
        productQna.setProduct(product);
        productQna.setMember(member);
        return productQna;
    }


}
