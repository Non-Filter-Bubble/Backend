package com.example.book_service.dto.mybook;

import com.example.book_service.domain.bookbox.BookboxEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MybookUpdateRequestDto {

    private String comment;
    private String review;

    @Builder
    public MybookUpdateRequestDto(String comment, String review) {
        this.comment = comment;
        this.review = review;
    }
}
