package com.example.book_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동 아이디값  생성
    private int userid;

    private String loginid; //아이디
    private String username;    //닉네임
    private String password;    //비번
    private String role;


}
