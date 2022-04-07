package com.example.NGGG.repository.ProductImg;

import com.example.NGGG.domain.ProductImg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class ProductImgQueryRepository {

    private final EntityManager em;

    //productNo를 통해 이미지(main, detail) 가져오기
    public List<ProductImg> findByProductNo(int productNo) {
        return em.createQuery(
                "select i from ProductImg i" +
                        " join i.product p" +
                        " on p.no = :no", ProductImg.class)
                .setParameter("no", productNo)
                .getResultList();
    }

    public int deleteByProductNo(int productNo) {
        return em.createQuery(
                "delete from ProductImg i" +
                        " join i.product p" +
                        " where p.no = :no")
                .setParameter("no", productNo)
                .executeUpdate();
    }
}
