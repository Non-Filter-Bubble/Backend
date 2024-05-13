package com.example.book_service.entity;

import com.example.book_service.domain.bookbox.BookboxEntity;
import com.example.book_service.domain.bookmark.BookmarkEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동 아이디값  생성
    private int userid;

    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String nickname;
    private String password;
    private String role;

    // 북마크
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookmarkEntity> bookmark = new ArrayList<>();

    // 사용자 선호 장르
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GenreEntity> genre = new ArrayList<>();

    //북서랍
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookboxEntity> bookbox = new ArrayList<>();


    @Override
    public String toString() {
        return "UserEntity{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +  // 비밀번호는 로그에 남기지 않는 것이 보안상 좋습니다.
                ", role='" + role + '\'' +
                '}';
    }
}

