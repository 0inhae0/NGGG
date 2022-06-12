package com.example.NGGG.common.security;

import com.example.NGGG.domain.Admin;
import com.example.NGGG.domain.Member;
import com.example.NGGG.repository.AdminRepository;
import com.example.NGGG.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUserByUsername(String type, String no) throws UsernameNotFoundException {

        //회원일 때
        if(type.equals(UserType.MEMBER.toString())) {

            return memberRepository.findById(Integer.parseInt(no))
                    .orElseThrow(() -> new UsernameNotFoundException("Member Not Found"));
        }

        //관리자일 때
        else {
            return adminRepository.findById(Integer.parseInt(no))
                    .orElseThrow(() -> new UsernameNotFoundException("Admin Not Found"));
        }

    }

}
