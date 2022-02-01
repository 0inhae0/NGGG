package com.example.NGGG.domain;

import lombok.Getter;

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
public class Admin {

    @Id @GeneratedValue
    private int admin_no;

    private String admin_name;

    private String admin_id;

    private String admin_pwd;

    private String admin_email;

    private LocalDateTime admin_joindate;

    @OneToMany(mappedBy = "admin")
    private List<ProductQna> productQnas = new ArrayList<>();
}
