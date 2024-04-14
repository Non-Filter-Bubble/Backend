package com.example.book_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BookboxEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookboxid;

    @ManyToOne
    @JoinColumn(name = "userid") // UserEntity의 자동 생성된 ID 값을 참조하는 외래 키
    private UserEntity user;

    private String genre;

}