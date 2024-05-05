package com.example.book_service.dto.mybook;


import com.example.book_service.domain.bookbox.BookboxEntity;
import com.example.book_service.domain.mybook.MybookEntity;
import com.example.book_service.domain.book.BookEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MybookSaveRequestDto {
    Long bookboxid;
    Long bookid;
    String comment;
    String review;
    Boolean evaluation;

    @Builder
    public MybookSaveRequestDto(Long bookboxid, Long bookid, String comment, String review, Boolean evaluation) {
        this.bookboxid = bookboxid;
        this.bookid = bookid;
        this.comment = comment;
        this.review = review;
        this.evaluation = evaluation;
    }

    public MybookEntity toEntity(BookboxEntity bookbox, BookEntity book) {
        return MybookEntity.builder()
                .bookbox(bookbox)
                .book(book)
                .comment(comment)
                .review(review)
                .evaluation(evaluation)
                .build();
    }


}
