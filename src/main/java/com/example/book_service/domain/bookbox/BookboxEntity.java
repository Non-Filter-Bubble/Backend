package com.example.book_service.domain.bookbox;

import com.example.book_service.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
@Entity
public class BookboxEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookboxid;

    @ManyToOne
    @JoinColumn(name = "userid") // UserEntity의 자동 생성된 ID 값을 참조하는 외래 키
    private UserEntity user;

    private String genre;

    @Builder
    public BookboxEntity(UserEntity user, String genre) {
        this.user = user;
        this.genre = genre;
    }

}