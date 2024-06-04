package com.example.book_service.service.AI;

import com.example.book_service.entity.UserRecommendation;
import com.example.book_service.repository.UserRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRecommendationService {
    @Autowired
    private UserRecommendationRepository userRecommendationRepository;

    public UserRecommendation saveUserRecommendation(UserRecommendation userRecommendation) {
        return userRecommendationRepository.save(userRecommendation);
    }
}
