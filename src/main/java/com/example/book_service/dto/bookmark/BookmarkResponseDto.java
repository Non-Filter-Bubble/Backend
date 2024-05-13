package com.example.book_service.dto.bookmark;


import com.example.book_service.domain.bookmark.BookmarkEntity;
import lombok.Getter;

@Getter
public class BookmarkResponseDto {
    private Long bookmarkid;
    private Long isbn;
    private int userid;
    private Boolean main_screen_selected;
    private Boolean search_screen_selected;

    public BookmarkResponseDto(BookmarkEntity entity) {
        this.bookmarkid = entity.getBookmarkid();
        this.isbn = entity.getIsbn();
        this.userid = entity.getUser().getUserid();
        this.main_screen_selected = entity.isMain_screen_selected();
        this.search_screen_selected = entity.isSearch_screen_selected();
    }
}
