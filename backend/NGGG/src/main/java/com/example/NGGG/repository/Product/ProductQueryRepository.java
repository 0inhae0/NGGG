package com.example.NGGG.repository.Product;

import com.example.NGGG.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class ProductQueryRepository {

    private final EntityManager em;
    /*
    //CategoryNo로 Product 가져오기
    public List<Product> findByCategory(int categoryNo) {
        return em.createQuery(
                "select p from Product p" +
                        " join p.category c" +
                        " where c.no = :no", Product.class)
                .setParameter("no", categoryNo)
                .getResultList();
    }*/

    //categoryNo 기준으로 조인, order by 정렬, 페이징       ->나중에 각각 productImg 지연로딩으로 조회
    public List<Product> findByCategoryOrdered(int categoryNo, int offset, int size, int sort) {
        switch(sort) {
            case 0://인기순
                return em.createQuery(
                        "select p from Product p" +
                                " join p.category c" +
                                " where c.no = :no" +
                                " order by p.productLikeCnt desc", Product.class)
                        .setParameter("no", categoryNo)
                        .setFirstResult(offset)
                        .setMaxResults(size)
                        .getResultList();
            case 1://신상품순
                return em.createQuery(
                                "select p from Product p" +
                                        " join p.category c" +
                                        " where c.no = :no" +
                                        " order by p.productRegdate desc", Product.class)
                        .setParameter("no", categoryNo)
                        .setFirstResult(offset)
                        .setMaxResults(size)
                        .getResultList();
            case 2://높은가격순
                return em.createQuery(
                                "select p from Product p" +
                                        " join p.category c" +
                                        " where c.no = :no" +
                                        " order by p.productPrice desc", Product.class)
                        .setParameter("no", categoryNo)
                        .setFirstResult(offset)
                        .setMaxResults(size)
                        .getResultList();
            case 3://낮은가격순
                return em.createQuery(
                                "select p from Product p" +
                                        " join p.category c" +
                                        " where c.no = :no" +
                                        " order by p.productPrice asc", Product.class)
                        .setParameter("no", categoryNo)
                        .setFirstResult(offset)
                        .setMaxResults(size)
                        .getResultList();
            default:
                return em.createQuery(
                                "select p from Product p" +
                                        " join p.category c" +
                                        " where c.no = :no", Product.class)
                        .setParameter("no", categoryNo)
                        .setFirstResult(offset)
                        .setMaxResults(size)
                        .getResultList();
        }
    }

    public long totalCount(int categoryNo) {
        return em.createQuery(
                "select count(p) from Product p" +
                        " join p.category c" +
                        " where c.no = :no", Long.class)
                .setParameter("no", categoryNo)
                .getSingleResult();
    }
}

