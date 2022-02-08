package com.example.NGGG.domain;

import com.example.NGGG.dto.UpdateAdminRequest;
import com.example.NGGG.dto.UpdateMemberRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor
public class Admin implements UserDetails {

    @Id @GeneratedValue
    @Column(name = "admin_no")
    private int no;

    private String adminId;

    @Setter
    private String adminPwd;

    @Setter
    private String adminName;

    private String adminEmail;

    @CreationTimestamp
    @Setter
    private LocalDate adminJoindate;

    //spring security
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "admin")
    private List<ProductQna> productQnas = new ArrayList<>();


    public void updateAdmin(UpdateAdminRequest request) {
        this.setAdminPwd(request.getAdminPwd());
        this.setAdminName(request.getAdminName());
    }

    public Admin(String adminId, String adminPwd, String adminName, String adminEmail, List<String> roles) {
        this.adminId = adminId;
        this.adminPwd = adminPwd;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
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
        return adminId;
    }

    @Override
    public String getPassword() {
        return adminPwd;
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
