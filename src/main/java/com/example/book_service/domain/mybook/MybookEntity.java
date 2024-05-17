package com.example.book_service.domain.mybook;


import com.example.book_service.bookinfoAPI.BookInfoEntity;
import com.example.book_service.domain.bookbox.BookboxEntity;
import com.example.book_service.entity.UserEntity;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "bookboxid")
public class MybookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mybookid;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 설정
    @JoinColumn(name = "bookboxid") // BookboxEntity 자동 생성된 ID 값을 참조하는 외래 키
    @JsonBackReference
    private BookboxEntity bookbox;

    private Long isbn;

    private String comment;

    @Column(length = 50) // review 필드의 길이를 50자로 제한
    private String review;

    private Boolean evaluation;

    private String title;
    private String author;
    private String publisher;

    private int userid; // 유저 ID

    public void update(String comment, String review) {
        this.comment = comment;
        this.review = review;
    }

    @Builder
    public MybookEntity(BookboxEntity bookbox, Long isbn, String comment, String review, Boolean evaluation, String title, String author, String publisher,int userid) {
        this.bookbox = bookbox;
        this.isbn = isbn;
        this.comment = comment;
        this.review = review;
        this.evaluation = evaluation;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.userid = userid;
    }

    @JsonProperty("bookboxid")
    public Long getBookboxId() {
        return this.bookbox != null ? this.bookbox.getBookboxid() : null;
    }
}
