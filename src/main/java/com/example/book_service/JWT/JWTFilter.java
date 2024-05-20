package com.example.book_service.JWT;

import com.example.book_service.dto.CustomUserDetails;
import com.example.book_service.entity.UserEntity;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //request에서 Authorization 헤더를 찾음
        String authorization= request.getHeader("Authorization");

        //Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("token null");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        System.out.println("authorization now");
        //Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];


        try {
            // 토큰이 만료되었는지 확인합니다.
            if (jwtUtil.isExpired(token)) {
                // 토큰이 만료된 경우 클라이언트에게 적절한 오류 응답을 반환합니다.
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token expired");
                return;
            }

            // 토큰이 유효한 경우 사용자를 인증하고 SecurityContext에 저장합니다.
            String username = jwtUtil.getUsername(token);
            String role = jwtUtil.getRole(token);

            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setPassword("temppassword");
            userEntity.setRole(role);

            CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

            Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우 클라이언트에게 적절한 오류 응답을 반환합니다.
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expired");
        }
    }








//        //토큰 소멸 시간 검증
//        if (jwtUtil.isExpired(token)) {
//
//            System.out.println("token expired");
//            filterChain.doFilter(request, response);
//
//            //조건이 해당되면 메소드 종료 (필수)
//            return;
//        }
//
//        //두개의 if문 다 거치면 최종적으로 토큰 검증 완료
//
//        //토큰에서 username과 role 획득
//        String username = jwtUtil.getUsername(token);
//        String role = jwtUtil.getRole(token);
//
//        System.out.printf("Username: %s, Role: %s%n", username, role);
//
//
//        //userEntity를 생성하여 값 set (초기화)
//        UserEntity userEntity = new UserEntity();
//        userEntity.setUsername(username);
//        userEntity.setPassword("temppassword"); //비번은 토큰에 담겨있지x, 임의로 지정)
//        userEntity.setRole(role);
//        System.out.println(userEntity);
//
//        //UserDetails에 회원 정보 객체 담기
//        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);
//        System.out.println("Created CustomUserDetails with UserEntity");
//
//        //스프링 시큐리티 인증 토큰 생성
//        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
//        System.out.println("Created Authentication Token");
//        //세션에 사용자 등록
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//        System.out.println("Authentication token set in SecurityContextHolder");
//        filterChain.doFilter(request, response); //다음 필터한테 넘겨주기
//
//        System.out.println("Proceeded to next filter");


    }

