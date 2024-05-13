package com.example.book_service.dto.mybook;


import com.example.book_service.domain.bookbox.BookboxEntity;
import com.example.book_service.domain.mybook.MybookEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MybookSaveRequestDto {
    Long bookboxid;
    Long isbn;
    String comment;
    String review;
    Boolean evaluation;
    String title;
    String author;
    String publisher;

    @Builder
    public MybookSaveRequestDto(Long bookboxid, Long isbn, String comment, String review, Boolean evaluation, String title, String author, String publisher) {
        this.bookboxid = bookboxid;
        this.isbn = isbn;
        this.comment = comment;
        this.review = review;
        this.evaluation = evaluation;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }

    public MybookEntity toEntity(BookboxEntity bookbox) {
        return MybookEntity.builder()
                .bookbox(bookbox)
                .isbn(isbn)
                .comment(comment)
                .review(review)
                .evaluation(evaluation)
                .title(title)
                .author(author)
                .publisher(publisher)
                .build();
    }


}
