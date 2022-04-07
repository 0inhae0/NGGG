package com.example.NGGG.repository;

import com.example.NGGG.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    boolean existsByAdminId(String adminId);
    boolean existsByAdminEmail(String adminEmail);
    Optional<Admin> findByAdminId(String adminId);
}
