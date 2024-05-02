package com.example.book_service.entity;

import com.example.book_service.domain.mybook.MybookEntity;
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

    @ManyToOne
    @JoinColumn(name = "mybookid")
    private MybookEntity mybook;



    private String ISBN_THIRTEEN_NO;
    private String GENRE_LV1; //ex.소설,에세이
    private String GENRE_LV2;   //로맨스,액션,여행
    private String INFO_TEXT; //한줄소개
    private String IMAGE;
}
