package com.example.book_service.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private SecretKey secretKey;
    //생성자 호출될때 application.properties에 저장했던 시크릿 키 불러오기
    public JWTUtil(@Value("${spring.jwt.secret}")String secret){
        //키를 객체타입으로 저장하면서 암호화 저장
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

    }

    public String getUsername(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

//    public Boolean isExpired(String token) {
//
//        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
//    }
public Boolean isExpired(String token) {
    try {
        // Set the signing key and parse the claims
        Claims claims = Jwts.parser()  // JwtParserBuilder 사용
                .setSigningKey(secretKey)              // 서명 키 설정
                .build()                         // JwtParser 생성
                .parseClaimsJws(token)           // 토큰 파싱
                .getBody();

        // Get the expiration date from the token
        Date expiration = claims.getExpiration();
        boolean isExpired = expiration.before(new Date());

        // Logging the expiration time for debugging
        System.out.println("Expiration time: " + expiration.toString() + " Current time: " + new Date().toString() + " Is expired: " + isExpired);

        return isExpired;
    } catch (ExpiredJwtException e) {
        // This block will catch if the token is already expired
        System.out.println("Token is expired: " + e.getMessage());
        return true;
    } catch (Exception e) {
        // Generic exception handler
        System.out.println("Error in token parsing or signature verification: " + e.getMessage());
        return true; // Consider the token as expired in case of any parsing error
    }
}

    public String createJwt(String username, String role, Long expiredMs) {

        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }
}
