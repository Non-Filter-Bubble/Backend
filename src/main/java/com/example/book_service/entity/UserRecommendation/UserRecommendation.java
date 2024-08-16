package com.example.book_service.entity.UserRecommendation;

import com.example.book_service.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter

public class UserRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userid",nullable = false) // UserEntity의 자동 생성된 ID 값을 참조하는 외래 키
    private UserEntity user;

    @OneToMany(mappedBy = "userRecommendation", cascade = CascadeType.ALL)
    private List<IsbnNonFilter> isbnNonFilter;

    @OneToMany(mappedBy = "userRecommendation", cascade = CascadeType.ALL)
    private List<IsbnFilter> isbnFilter;

    // Getter and Setter
}