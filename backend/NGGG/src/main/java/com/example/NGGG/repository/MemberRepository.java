package com.example.NGGG.repository;

import com.example.NGGG.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    boolean existsByMemberId(String memberId);
    boolean existsByMemberEmail(String memberEmail);
    boolean existsByMemberNickname(String memberNickname);
    Optional<Member> findByMemberId(String memberId);

}
