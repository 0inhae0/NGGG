package com.example.NGGG.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Admin {

    @Id @GeneratedValue
    @Column(name = "admin_no")
    private int no;

    private String adminName;

    private String adminId;

    private String adminPwd;

    private String adminEmail;

    private LocalDateTime adminJoindate;

    @OneToMany(mappedBy = "admin")
    private List<ProductQna> productQnas = new ArrayList<>();
}
