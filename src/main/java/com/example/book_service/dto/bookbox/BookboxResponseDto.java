package com.example.book_service.dto.bookbox;


import com.example.book_service.domain.bookbox.BookboxEntity;
import com.example.book_service.entity.UserEntity;
import lombok.Getter;

@Getter
public class BookboxResponseDto {
    private Long bookboxid;
    private UserEntity user;
    private String genre;

    public BookboxResponseDto(BookboxEntity entity) {
        this.bookboxid = entity.getBookboxid();
        this.user = entity.getUser();
        this.genre = entity.getGenre();
    }
}
