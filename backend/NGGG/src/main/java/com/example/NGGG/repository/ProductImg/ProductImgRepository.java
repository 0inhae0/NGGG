package com.example.NGGG.repository.ProductImg;

import com.example.NGGG.domain.Member;
import com.example.NGGG.domain.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImg, Integer> {
}
