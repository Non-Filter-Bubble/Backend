package com.example.book_service.dto.bookbox;

import com.example.book_service.domain.bookbox.BookboxEntity;
import com.example.book_service.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class BookboxSaveRequestDto {
    int userid;
    String genre;

    @Builder
    public BookboxSaveRequestDto(int userid, String genre) {
        this.userid = userid;
        this.genre = genre;
    }

    public BookboxEntity toEntity(UserEntity user) {
        return BookboxEntity.builder()
                .user(user)
                .genre(genre)
                .build();
    }
}
