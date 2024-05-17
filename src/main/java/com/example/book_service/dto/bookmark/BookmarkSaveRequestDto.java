package com.example.book_service.dto.bookmark;


import com.example.book_service.domain.bookmark.BookmarkEntity;
import com.example.book_service.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookmarkSaveRequestDto {
    private int userid;
    private Long isbn;
    private Boolean main_screen_selected = false;
    private Boolean search_screen_selected = false;
    @Builder
    public BookmarkSaveRequestDto(int userid, Long isbn, Boolean main_screen_selected, Boolean search_screen_selected) {
        this.userid = userid;
        this.isbn = isbn;
        this.main_screen_selected = main_screen_selected;
        this.search_screen_selected = search_screen_selected;
    }

    public BookmarkEntity toEntity(UserEntity user) {
        return BookmarkEntity.builder()
                .user(user)
                .isbn(isbn)
                .main_screen_selected(main_screen_selected)
                .search_screen_selected(search_screen_selected)
                .build();
    }



    public void setUserId(int userid) {
        this.userid = userid;
    }

    public int getUserId() {
        return this.userid;
    }
}
