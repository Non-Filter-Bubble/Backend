package com.example.book_service.domain.mybook;


import com.example.book_service.domain.bookbox.BookboxEntity;
import com.example.book_service.domain.book.BookEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class MybookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mybookid;

    @ManyToOne
    @JoinColumn(name = "bookboxid") // BookboxEntity 자동 생성된 ID 값을 참조하는 외래 키
    private BookboxEntity bookbox;

    @ManyToOne
    @JoinColumn(name = "bookid", unique = true) // BookEntity의 ID 값을 참조하는 외래 키
    private BookEntity book;

    private String comment;

    @Column(length = 50) // review 필드의 길이를 50자로 제한
    private String review;

    private Boolean evaluation;

    public void update(String comment, String review) {
        this.comment = comment;
        this.review = review;
    }

    @Builder
    public MybookEntity(BookboxEntity bookbox, BookEntity book, String comment, String review, Boolean evaluation) {
        this.bookbox = bookbox;
        this.book = book;
        this.comment = comment;
        this.review = review;
        this.evaluation = evaluation;
    }
}
