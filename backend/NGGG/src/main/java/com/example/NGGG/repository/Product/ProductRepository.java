package com.example.NGGG.repository.Product;

import com.example.NGGG.domain.Admin;
import com.example.NGGG.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    //productRegdate 내림차순 기준으로 상위 10개의 Product
    List<Product> findTop10ByOrderByProductRegdateDesc();


    //productLikeCnt 내림차순 기준으로 상위 10개의 Product
    List<Product> findTop10ByOrderByProductLikeCntDesc();

    Page<Product> findAll(Pageable pageable);

}
