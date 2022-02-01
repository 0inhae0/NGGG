package com.example.NGGG.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_no")
    private int no;

    private String memberName;

    private String memberId;

    private String memberPwd;

    private String memberTel;

    private String memberAddress;

    private String memberEmail;

    private LocalDateTime memberJoindate;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ProductQna> productQnas = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ProductReview> productReviews = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberProduct> memberProducts = new ArrayList<>();


}
