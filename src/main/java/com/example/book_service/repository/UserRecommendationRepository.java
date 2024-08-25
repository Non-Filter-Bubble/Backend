package com.example.book_service.repository;

import com.example.book_service.entity.UserEntity;
import com.example.book_service.entity.UserRecommendation.UserRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRecommendationRepository  extends JpaRepository<UserRecommendation, Long> {
    boolean existsByUserUserid(int userId);
    List<UserRecommendation> findByUser(UserEntity user);
}

