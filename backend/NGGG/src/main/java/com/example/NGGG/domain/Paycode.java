package com.example.NGGG.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Paycode {

    @Id @GeneratedValue
    private int paycode_no;

    @NotEmpty
    private String paycode_name; //무통장입금, 신용카드, 카카오페이

    @OneToMany(mappedBy = "paycode")
    private List<Pay> payList = new ArrayList<>();
}
