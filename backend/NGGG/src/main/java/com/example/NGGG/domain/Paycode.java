package com.example.NGGG.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Paycode {

    @Id @GeneratedValue
    @Column(name = "paycode_no")
    private int no;

    private String paycodeName; //무통장입금, 신용카드, 카카오페이

    @OneToMany(mappedBy = "paycode")
    private List<Pay> payList = new ArrayList<>();
}
