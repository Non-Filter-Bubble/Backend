package com.example.book_service.repository;

import com.example.book_service.entity.UserRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRecommendationRepository extends JpaRepository<UserRecommendation, Long> {
}
