package com.example.NGGG.controller;

import com.example.NGGG.domain.Admin;
import com.example.NGGG.dto.LoginAdminResponse;
import com.example.NGGG.dto.UpdateAdminRequest;
import com.example.NGGG.service.AdminService;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.time.LocalDate;

import static com.example.NGGG.controller.AdminApiController.CreateAdminRequest.createAdmin;
import static java.util.Collections.singletonList;

@RestController
@RequiredArgsConstructor
public class AdminApiController {

    private final AdminService adminService;

    /**
     * 로그인
     */
    @PostMapping("/admin/login")
    public LoginAdminResponse loginAdmin(@RequestBody LoginAdminRequest request) {
        LoginAdminResponse response = adminService.login(request.getAdminId(), request.getAdminPwd());
        return response;
    }

    /**
     * 회원가입
     */
    @PostMapping("/admin/signup")
    public CreateAdminResponse saveAdmin(@RequestBody @Valid CreateAdminRequest request) {

        Admin admin = createAdmin(request);

        if (adminService.checkIdDuplicate(request.getAdminId())
                || adminService.checkEmailDuplicate(request.getAdminEmail())) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        } else {
            int no = adminService.join(admin);
            return new CreateAdminResponse(no);
        }

    }

    /**
     * 아이디 중복 확인
     */
    @PostMapping("/admin/idcheck")
    public ResponseEntity<Boolean> checkIdDuplicate(@RequestBody String id) {
        //중복인 경우 true, 아니면 false
        return ResponseEntity.ok(adminService.checkIdDuplicate(id));
    }

    /**
     * 이메일 중복 확인
     */
    @PostMapping("/admin/emailcheck")
    public ResponseEntity<Boolean> checkEmailDuplicate(@RequestBody String email) {
        //중복인 경우 true, 아니면 false
        return ResponseEntity.ok(adminService.checkEmailDuplicate(email));
    }

    /**
     * 개인정보 수정
     */
    @PostMapping("/admin/update/{admin_no}")
    public AdminResponse updateAdmin(
            @PathVariable("admin_no") int no,
            @RequestBody @Valid UpdateAdminRequest request) {

        adminService.update(no, request);
        Admin findAdmin = adminService.findOne(no);
        return new AdminResponse(findAdmin);
    }

    /**
     * 개인정보 조회
     */
    @GetMapping("/admin/{admin_no}")
    public AdminResponse viewAdmin(@PathVariable("admin_no") int no) {
        Admin findAdmin = adminService.findOne(no);
        return new AdminResponse(findAdmin);
    }


    //DTO for 로그인(request)
    @Data
    static class LoginAdminRequest {

        @NotBlank
        private String adminId;

        @NotBlank
        private String adminPwd;
    }

    //DTO for 회원가입(response)
    @Data
    static class CreateAdminResponse {
        private int adminNo;

        public CreateAdminResponse(int no) {
            this.adminNo = no;
        }
    }

    //DTO for 회원가입(request)
    @Data
    static class CreateAdminRequest {

        @NotBlank(message = "아이디를 입력해주세요.")
        @Size(min = 6, message = "6자 이상의 아이디를 입력해주세요.")
        private String adminId;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String adminPwd;

        @NotBlank(message = "이름을 입력해주세요.")
        private String adminName;

        @NotBlank(message = "이메일을 입력해주세요.")
        @Email(message = "올바른 이메일 주소를 입력해주세요.")
        private String adminEmail;

        public static Admin createAdmin(CreateAdminRequest request) {

            Admin admin = new Admin(request.getAdminId(), request.getAdminPwd(), request.getAdminName(), request.getAdminEmail(), singletonList("ROLE_ADMIN"));
            return admin;

        }

    }


    //DTO for 개인정보 수정(response), 개인정보 조회(response)
    @Getter
    static class AdminResponse {

        private String adminId;

        private String adminName;

        private String adminEmail;

        private LocalDate adminJoindate;

        public AdminResponse(Admin admin) {
            this.adminId = admin.getAdminId();
            this.adminName = admin.getAdminName();
            this.adminEmail = admin.getAdminEmail();
            this.adminJoindate = admin.getAdminJoindate();
        }
    }


}
