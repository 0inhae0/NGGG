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

    @NotEmpty
    private String admin_name;

    @NotEmpty
    private String admin_id;

    @NotEmpty
    private String admin_pwd;

    @NotEmpty
    private String admin_email;

    @NotEmpty
    private LocalDateTime admin_joindate;

    @OneToMany(mappedBy = "admin")
    private List<ProductQna> productQnas = new ArrayList<>();
}
