package com.example.book_service.entity;

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

    @ElementCollection
    private List<Long> isbnNonFilter;

    @ElementCollection
    private List<Long> isbnFilter;
}
