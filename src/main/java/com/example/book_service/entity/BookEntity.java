package com.example.book_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BookEntity {
    //실제 db에 들어가는건 title,isbn.book_genre,sentence
    //나머지 값들은 api로 끌고 오기
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookid;


    private String title;
    private String isbn;
    private String author;
    private String publisher;
    private String summary;
    private String bookcover; //이미지 주소
    private String book_genre;
    private String sentence; //한줄소개
}
