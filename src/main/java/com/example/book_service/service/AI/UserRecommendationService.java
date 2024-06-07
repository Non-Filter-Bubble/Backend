package com.example.book_service.service.AI;

import com.example.book_service.entity.UserRecommendation.UserRecommendation;
import com.example.book_service.repository.UserRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRecommendationService {

    private final UserRecommendationRepository userRecommendationRepository;

    @Autowired
    public UserRecommendationService(UserRecommendationRepository userRecommendationRepository) {
        this.userRecommendationRepository = userRecommendationRepository;
    }
    // user_id에 해당하는 레코드가 존재하는지 확인하는 메서드
    public boolean existsByUserId(int userId) {
        return userRecommendationRepository.existsByUserUserid(userId);
    }


    public void saveUserRecommendation(UserRecommendation userRecommendation) {
        userRecommendationRepository.save(userRecommendation);
    }
}