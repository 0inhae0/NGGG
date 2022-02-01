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
public class Category {

    @Id @GeneratedValue
    private int category_no;

    @NotEmpty
    private String category_name;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();
}
