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

    private String loginid;
    private String username;
    private String password;
    private String role;


}

