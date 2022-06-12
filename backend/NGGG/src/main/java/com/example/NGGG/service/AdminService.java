package com.example.NGGG.service;

import com.example.NGGG.common.security.JwtTokenProvider;
import com.example.NGGG.common.security.UserType;
import com.example.NGGG.domain.Admin;
import com.example.NGGG.domain.ProductQna;
import com.example.NGGG.dto.LoginAdminResponse;
import com.example.NGGG.dto.UpdateAdminRequest;
import com.example.NGGG.exception.NotFoundException;
import com.example.NGGG.exception.WrongArgException;
import com.example.NGGG.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 로그인
     */
    public LoginAdminResponse login(String id, String pwd) {
        Admin findAdmin = adminRepository.findByAdminId(id)
                .orElseThrow(() -> new NotFoundException("ID Not Found"));
        if(!passwordEncoder.matches(pwd, findAdmin.getAdminPwd())) {
            //비밀번호가 일치하지 않음
            throw new WrongArgException("Wrong Password");
        }
        String token = jwtTokenProvider.createToken(UserType.ADMIN.toString(), findAdmin.getUsername(), findAdmin.getRoles());
        int adminNo = findAdmin.getNo();
        return new LoginAdminResponse(token, adminNo);
    }

    /**
     * 회원가입
     */
    public int join(Admin admin) {
        //비밀번호 암호화
        String encodedAdminPwd = passwordEncoder.encode(admin.getAdminPwd());
        admin.setAdminPwd(encodedAdminPwd);

        adminRepository.save(admin);
        return admin.getNo();
    }

    /**
     * 아이디 중복 확인
     */
    public boolean checkIdDuplicate(String id) {
        return adminRepository.existsByAdminId(id);
    }

    /**
     * 이메일 중복 확인
     */
    public boolean checkEmailDuplicate(String email) {
        return adminRepository.existsByAdminEmail(email);
    }

    /**
     * 회원 수정
     */
    @Transactional
    public void update(int no, UpdateAdminRequest request) {
        Admin findAdmin = adminRepository.findById(no)
                .orElseThrow(() -> new NotFoundException("Admin Not Found"));
        //비밀번호 암호화
        String encodedAdminPwd = passwordEncoder.encode(request.getAdminPwd());
        request.setAdminPwd(encodedAdminPwd);

        findAdmin.updateAdmin(request);
    }

    /**
     * 회원 한명 조회
     */
    public Admin findOne(int no) {
        Admin findAdmin = adminRepository.findById(no)
                .orElseThrow(() -> new NotFoundException("Admin Not Found"));
        return findAdmin;
    }

    /**
     * 회원 삭제
     */
    public void deleteAdmin(int no) {
        Admin findAdmin = adminRepository.findById(no)
                .orElseThrow(() -> new NotFoundException("Admin Not Found"));
        //연관관계 끊기
        findAdmin.getProductQnas().stream()
                        .forEach(q -> q.setAdmin(null));
        adminRepository.deleteById(no);
    }
}
