package com.example.NGGG.common.constant;

//api 수정시 수정
public class MappingConstant {

    //로그인 없이 다 가능한 것들
    public static String[] All = {

            "/member/login",
            "/product/{product_no}",
            "/product/list",
            "/product/new",
            "/product/best",
            "/product/search",
            "/review/{product_no}",
            "/product/{product_no}/qna",
            "/member/signup",
            "/member/namecheck",
            "/member/idcheck",
            "/member/emailcheck",
            "/admin/signup",
            "/admin/login",
            "/admin/idcheck",
            "/admin/emailcheck",
            "/main/{img_name}",
            "/detail/{img_name}"

    };
    
    //회원만 가능
    public static String[] Member = {
            "/review/add",
            "/review/{member_no}",
            "/qna/add",
            "/member/{member_no}/qna",
            "/qna/{product_qna_no}/delete",
            "/member/{member_no}/update",
            "/like/{member_no}",
            "/cart/{member_no}",
            "/order/{member_no}",
            "/order/{member_no}/pay",
            "/member/{member_no}/orderhistory",
            "/member/{member_no}/delete"
            
    };
    
    //관리자만 가능
    public static String[] Admin = {
            "/product/add",
            "/product/update",
            "/product/{product_no}/delete",
            "/qna/answer",
            "/admin/{admin_no}",
            "/admin/{admin_no}/update",
            "/admin/{admin_no}/delete"
    };

    //회원, 관리자 둘 다 가능
    public static String[] User = {
            "/review/delete/{member_no}/{review_no}",
            "/qna/delete/{qna_no}",
            "/member/{member_no}",
            "/member/{member_no}/orderhistory",
            "/member/{member_no}/orderhistory/{order_no}"
    };
}
