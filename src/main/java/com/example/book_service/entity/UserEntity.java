package com.example.book_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동 아이디값  생성
    private int userid;

    private String username;
    private String nickname;
    private String password;
    private String role;


    @Override
    public String toString() {
        return "UserEntity{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +  // 비밀번호는 로그에 남기지 않는 것이 보안상 좋습니다.
                ", role='" + role + '\'' +
                '}';
    }
}

