package com.example.NGGG.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    private int member_no;

    @NotEmpty
    private String member_name;

    @NotEmpty
    private String member_id;

    @NotEmpty
    private String member_pwd;

    @NotEmpty
    private String member_tel;

    @NotEmpty
    private String member_address;

    @NotEmpty
    private String member_email;

    @NotEmpty
    private LocalDateTime member_joindate;

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
