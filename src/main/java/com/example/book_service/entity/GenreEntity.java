package com.example.book_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Entity
@Setter
@Getter
public class GenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreid;

    @ManyToOne
    @JoinColumn(name = "userid",nullable = false) // UserEntity의 자동 생성된 ID 값을 참조하는 외래 키
    private UserEntity user;

    private String favGenre;
    private String favBookType;


    public void setFavGenre(List<String> genres) {
        this.favGenre = String.join(", ", genres); // 리스트를 쉼표로 구분된 문자열로 결합
    }

    public void setFavBookType(List<String> bookTypes) {
        this.favBookType = String.join(", ", bookTypes); // 리스트를 쉼표로 구분된 문자열로 결합
    }

    public List<String> getFavGenreList() {
        return Arrays.asList(favGenre.split(", "));
    }
}
