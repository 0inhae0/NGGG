package com.example.NGGG.repository.ProductQna;

import com.example.NGGG.domain.ProductQna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductQnaRepository extends JpaRepository<ProductQna, Integer> {
}
