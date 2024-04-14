package com.example.book_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookid;


    private String title;
    private String author;
    private String publisher;
    private String summary;
    private String bookcover; //이미지 주소
    private String book_genre;
}
