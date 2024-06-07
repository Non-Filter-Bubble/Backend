package com.example.book_service.entity.UserRecommendation;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class IsbnFilter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_recommendation_id")
    private UserRecommendation userRecommendation;

    @Column(name = "isbn")
    private Long isbn;

    // Getter and Setter
}
