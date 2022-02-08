package com.example.NGGG.service;

import com.example.NGGG.common.security.JwtTokenProvider;
import com.example.NGGG.domain.Admin;
import com.example.NGGG.dto.LoginAdminResponse;
import com.example.NGGG.dto.UpdateAdminRequest;
import com.example.NGGG.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

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
     * 로그인
     */

    public LoginAdminResponse login(String id, String pwd) {
        Admin findAdmin = adminRepository.findByAdminId(id)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 아이디입니다."));
        if(!passwordEncoder.matches(pwd, findAdmin.getAdminPwd())) {
            //비밀번호가 일치하지 않음
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        String token = jwtTokenProvider.createToken("A", findAdmin.getUsername(), findAdmin.getRoles());
        int adminNo = findAdmin.getNo();
        return new LoginAdminResponse(token, adminNo);
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
        Admin findAdmin = adminRepository.findById(no);
        //비밀번호 암호화
        String encodedAdminPwd = passwordEncoder.encode(request.getAdminPwd());
        request.setAdminPwd(encodedAdminPwd);
        findAdmin.updateAdmin(request);
    }

    /**
     * 회원 한명 조회
     */
    public Admin findOne(int no) {
        return adminRepository.findById(no);
    }
}
