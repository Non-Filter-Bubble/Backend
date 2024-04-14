package com.example.book_service.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class MybookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mybookid;

    @ManyToOne
    @JoinColumn(name = "bookboxid") // BookboxEntity 자동 생성된 ID 값을 참조하는 외래 키
    private BookboxEntity bookbox;

    @ManyToOne
    @JoinColumn(name = "bookid") // BookEntity 자동 생성된 ID 값을 참조하는 외래 키
    private BookEntity book;

    private String comment;

    @Column(length = 50) // review 필드의 길이를 50자로 제한
    private String review;

    private Boolean evaluation;
}
