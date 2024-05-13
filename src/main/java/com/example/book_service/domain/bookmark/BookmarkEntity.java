package com.example.book_service.domain.bookmark;

import com.example.book_service.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

@Getter
@NoArgsConstructor
@Entity
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

    @Builder
    public BookmarkEntity(UserEntity user, Long isbn, boolean main_screen_selected, boolean search_screen_selected) {
        this.user = user;
        this.isbn = isbn;
        this.main_screen_selected = main_screen_selected;
        this.search_screen_selected = search_screen_selected;


    }

}
