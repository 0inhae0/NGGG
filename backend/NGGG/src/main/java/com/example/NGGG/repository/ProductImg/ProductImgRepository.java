package com.example.NGGG.repository.ProductImg;

import com.example.NGGG.domain.Member;
import com.example.NGGG.domain.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImg, Integer> {

    //자꾸 ProductImg 테이블을 찾을 수 없다고 뜸...
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from ProductImg i" +
                    " join i.product p" +
                    " where p.no = :no", nativeQuery = true)
    void deleteByProductNo(@Param("no") int productNo);
}
