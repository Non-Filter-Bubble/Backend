package com.example.book_service.service;

import com.example.book_service.JWT.JWTUtil;
import com.example.book_service.entity.UserEntity;
import com.example.book_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserDetailsService userDetailsService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean validatePassword(String username, String rawPassword) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);  // 유저네임을 이용하여 UserDetails 로드

        if (userDetails != null && encoder.matches(rawPassword, userDetails.getPassword())) {
            return true;  // 비밀번호가 일치함
        }
        return false;  // 비밀번호 불일치 또는 사용자 없음
    }


}
