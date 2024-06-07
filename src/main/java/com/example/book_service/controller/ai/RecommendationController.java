package com.example.book_service.controller.ai;

import com.example.book_service.JWT.JWTUtil;

import com.example.book_service.dto.RecommendationResponse;
import com.example.book_service.entity.UserEntity;
import com.example.book_service.entity.UserRecommendation.IsbnFilter;
import com.example.book_service.entity.UserRecommendation.IsbnNonFilter;
import com.example.book_service.entity.UserRecommendation.UserRecommendation;
import com.example.book_service.service.AI.UserRecommendationService;
import com.example.book_service.service.GenreService;
import com.example.book_service.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;


import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/recommend")
public class RecommendationController {
    @Autowired
    private GenreService genreService;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserRecommendationService userRecommendationService;

    private final WebClient webClient = WebClient.create("http://3.37.204.233:8000/ai/books"); // AI 서비스 URL

    @GetMapping
    public ResponseEntity<?> getRecommendations(@RequestHeader("Authorization") String token) {
        try {
            String cleanedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
            String username = jwtUtil.getUsername(cleanedToken);
//            int userId = userService.getUserIdByUsername(username);
            UserEntity user = userService.getUserByUsername(username);

            // user_id가 중복되는지 확인하고 중복된 경우 에러 반환
            if (userRecommendationService.existsByUserId(user.getUserid())) {
                return ResponseEntity.status(400).body("UserRecommendation with user_id already exists");
            }


            List<String> favoriteGenres = genreService.getFavoriteGenresByUserId(user.getUserid());

            System.out.println("userId : " + user.getUserid());
            System.out.println("username : " + username);
            System.out.println("favoriteGenres : " + favoriteGenres);

            String requestBody = "{\"user_id\":" + user.getUserid() + ",\"genres\":" + new ObjectMapper().writeValueAsString(favoriteGenres) + "}";

            // JSON 요청의 형식 확인
//            JSONObject requestBody = new JSONObject();
//            requestBody.put("user_id", userId);
//            requestBody.put("genres", new JSONArray(favoriteGenres));
//            System.out.println("JSON 요청: " + requestBody);


            // AI 엔드포인트로 POST 요청 보내기
            System.out.println("Request sent to AI service");
            RecommendationResponse response = webClient.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(RecommendationResponse.class)
                    .block();

            System.out.println("Response from AI service: " + response);



            if (response != null) {
                UserRecommendation userRecommendation = new UserRecommendation();
                userRecommendation.setUser(user);

                // IsbnNonFilter 및 IsbnFilter 엔티티의 리스트로 변환하여 설정
                List<IsbnNonFilter> isbnNonFilterList = convertIsbnListToIsbnNonFilterList(response.getIsbn_nonfilter());
                List<IsbnFilter> isbnFilterList = convertIsbnListToIsbnFilterList(response.getIsbn_filter());

                userRecommendation.setIsbnNonFilter(isbnNonFilterList);
                userRecommendation.setIsbnFilter(isbnFilterList);

                // 각 IsbnNonFilter 및 IsbnFilter 엔티티에 대한 참조 설정
                for (IsbnNonFilter isbnNonFilter : isbnNonFilterList) {
                    isbnNonFilter.setUserRecommendation(userRecommendation);
                }

                for (IsbnFilter isbnFilter : isbnFilterList) {
                    isbnFilter.setUserRecommendation(userRecommendation);
                }

                userRecommendationService.saveUserRecommendation(userRecommendation);
            }


            return ResponseEntity.ok(response);
        } catch (WebClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    // Long 형식의 ISBN 목록을 IsbnNonFilter 엔티티의 리스트로 변환하는 메서드
    private List<IsbnNonFilter> convertIsbnListToIsbnNonFilterList(List<Long> isbnList) {
        List<IsbnNonFilter> result = new ArrayList<>();
        for (Long isbn : isbnList) {
            IsbnNonFilter isbnNonFilter = new IsbnNonFilter();
            isbnNonFilter.setIsbn(isbn);
            result.add(isbnNonFilter);
        }
        return result;
    }

    // Long 형식의 ISBN 목록을 IsbnFilter 엔티티의 리스트로 변환하는 메서드
    private List<IsbnFilter> convertIsbnListToIsbnFilterList(List<Long> isbnList) {
        List<IsbnFilter> result = new ArrayList<>();
        for (Long isbn : isbnList) {
            IsbnFilter isbnFilter = new IsbnFilter();
            isbnFilter.setIsbn(isbn);
            result.add(isbnFilter);
        }
        return result;
    }
}




