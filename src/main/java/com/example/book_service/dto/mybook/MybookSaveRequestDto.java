package com.example.book_service.dto.mybook;


import com.example.book_service.domain.bookbox.BookboxEntity;
import com.example.book_service.domain.mybook.MybookEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    // 사용자 ID 설정을 위한 setter 메서드 추가
    @Setter
    private int userid; // 사용자 ID 필드 추가

    @Builder
    public MybookSaveRequestDto(Long bookboxid, Long isbn, String comment, String review, Boolean evaluation, String title, String author, String publisher, int userid) {
        this.bookboxid = bookboxid;
        this.isbn = isbn;
        this.comment = comment;
        this.review = review;
        this.evaluation = evaluation;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.userid = userid; // 사용자 ID 설정
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
                .userid(userid) // userid 설정 추가
                .build();
    }


}
