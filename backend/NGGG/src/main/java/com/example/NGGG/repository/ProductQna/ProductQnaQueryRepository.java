package com.example.NGGG.repository.ProductQna;

import com.example.NGGG.dto.QnaByMemberNoQueryDto;
import com.example.NGGG.dto.QnaByProductNoQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class ProductQnaQueryRepository {

    private final EntityManager em;

    //productNo를 기준으로 Member, Admin, Product 조인해서 가져옴
    public List<QnaByProductNoQueryDto> findByProductNo(int productNo, int offset, int size) {
        return em.createQuery(
                "select new com.example.NGGG.dto.QnaByProductNoQueryDto(q.no, m.memberNickname, q.productQnaQue, a.adminName, q.productQnaAns, q.qnaRegdate)" +
                        " from ProductQna q" +
                        " join q.member m" +
                        " left join q.admin a" +
                        " join q.product p" +
                        " where p.no = :no" +
                        " order by q.qnaRegdate desc", QnaByProductNoQueryDto.class)
                .setParameter("no", productNo)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }

    //memberNo를 기준으로 Member, Admin, Proudct 조인해서 가져옴 **mainImg는 나중에 가져옴
    public List<QnaByMemberNoQueryDto> findByMemberNo(int memberNo, int offset, int size) {
        return em.createQuery(
                "select new com.example.NGGG.dto.QnaByMemberNoQueryDto(p.no, p.productName, q.productQnaQue, a.adminName, q.productQnaAns, q.qnaRegdate)" + //대표이미지 추가
                        " from ProductQna q" +
                        " join q.member m" +
                        " left join q.admin a" +
                        " join q.product p" +
                        " where m.no = :no" +
                        " order by q.qnaRegdate desc", QnaByMemberNoQueryDto.class)
                .setParameter("no", memberNo)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }

    public long totalCountByProductNo(int productNo) {
        return em.createQuery(
                "select count(q) from ProductQna q" +
                        " join q.product p" +
                        " where p.no = :no", Long.class)
                .setParameter("no", productNo)
                .getSingleResult();
    }

    public long totalCountByMemberNo(int memberNo) {
        return em.createQuery(
                        "select count(q) from ProductQna q" +
                                " join q.member m" +
                                " where m.no = :no", Long.class)
                .setParameter("no", memberNo)
                .getSingleResult();
    }
}
