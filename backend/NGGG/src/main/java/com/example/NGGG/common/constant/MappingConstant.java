package com.example.NGGG.common.constant;

//api 수정시 수정
public class MappingConstant {

    //로그인 없이 다 가능한 것들
    public static String[] All = {

            "/member/login",
            "/product/select/{product_no}",
            "/product/list",
            "/product/new",
            "/product/best",
            "/product/search",
            "/review/{product_no}",
            "/qna/{product_no}",
            "/member/signup",
            "/member/namecheck",
            "/member/idcheck",
            "/member/emailcheck"
    };
    
    //회원만 가능
    public static String[] Member = {
            "/review/add",
            "/review/{member_no}",
            "/qna/add",
            "/qna/{member_no}",
            "/member/update/{member_no}",
            "/like/{member_no}",
            "/cart/{member_no}",
            "/order/{member_no}",
            "/order/{member_no}/pay",
            "/member/{member_no}/orderhistory"
            
    };
    
    //관리자만 가능
    public static String[] Admin = {
            "/product/add",
            "/product/update",
            "/product/image/{product_no}",
            "/product/delete/{product_no}",
            "/qna/answer",
    };

    //회원, 관리자 둘 다 가능
    public static String[] User = {
            "/review/delete/{member_no}/{review_no}",
            "/qna/delete/{qna_no}",
            "/member/{member_no}",
            "/member/{member_no}/orderhistory",
            "member/{member_no}/orderhistory/{order_no}"
    };
}
