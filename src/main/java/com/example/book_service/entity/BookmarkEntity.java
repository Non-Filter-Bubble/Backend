package com.example.book_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BookmarkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmarkid;

    @ManyToOne
    @JoinColumn(name = "userid",nullable = false) // UserEntity의 자동 생성된 ID 값을 참조하는 외래 키
    private UserEntity user;

    private Long isbn;

    private boolean main_screen_selected;   // 메인 화면에서 선택한 경우
    private boolean search_screen_selected; // 검색 화면에서 선택한 경우


}
