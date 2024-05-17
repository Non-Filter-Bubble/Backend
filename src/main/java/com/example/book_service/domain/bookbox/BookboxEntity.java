package com.example.book_service.domain.bookbox;

import com.example.book_service.domain.mybook.MybookEntity;
import com.example.book_service.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@NoArgsConstructor
@Entity
public class BookboxEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookboxid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid",nullable = false) // UserEntity의 자동 생성된 ID 값을 참조하는 외래 키
    private UserEntity user;

    @OneToMany(mappedBy = "bookbox", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference  // 양방향 참조에서 부모 엔티티의 참조를 표시
    private List<MybookEntity> mybookList;

    private String genre;

    @Builder
    public BookboxEntity(UserEntity user, String genre) {
        this.user = user;
        this.genre = genre;
    }

}