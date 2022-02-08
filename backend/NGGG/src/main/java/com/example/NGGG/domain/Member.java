package com.example.NGGG.domain;

import com.example.NGGG.dto.UpdateMemberRequest;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
public class Member implements UserDetails {

    @Id @GeneratedValue
    @Column(name = "member_no")
    private int no;

    private String memberId;

    @Setter
    private String memberPwd;

    @Setter
    private String memberName;

    @Setter
    private String memberNickname;

    private String memberTel;

    @Column(unique = true)
    private String memberEmail;

    @Setter
    private String memberAddress;

    @CreationTimestamp
    @Setter
    private LocalDate memberJoindate;

    //spring security
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

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

    public void updateMember(UpdateMemberRequest request) {
        this.setMemberPwd(request.getMemberPwd());
        this.setMemberName(request.getMemberName());
        this.setMemberNickname(request.getMemberNickname());
        this.setMemberAddress(request.getMemberAddress());
    }

    public Member(String memberId, String memberPwd, String memberName, String memberNickname, String memberTel, String memberEmail, String memberAddress, List<String> roles) {

        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.memberName = memberName;
        this.memberNickname = memberNickname;
        this.memberTel = memberTel;
        this.memberEmail = memberEmail;
        this.memberAddress = memberAddress;
        this.roles = roles;
    }


    //spring security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return memberId;
    }

    @Override
    public String getPassword() {
        return memberPwd;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
