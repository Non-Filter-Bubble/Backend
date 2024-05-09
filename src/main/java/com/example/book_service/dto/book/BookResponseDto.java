package com.example.book_service.dto.book;

import com.example.book_service.domain.book.BookEntity;
import lombok.Getter;

@Getter
public class BookResponseDto {
    private String bookname;
    private String author;
    private String publisher;

    public BookResponseDto(BookEntity entity) {

    }
}
