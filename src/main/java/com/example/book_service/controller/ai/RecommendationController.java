package com.example.book_service.controller.ai;

import com.example.book_service.JWT.JWTUtil;

import com.example.book_service.dto.RecommendationResponse;
import com.example.book_service.entity.UserEntity;
import com.example.book_service.entity.UserRecommendation;
import com.example.book_service.service.AI.UserRecommendationService;
import com.example.book_service.service.GenreService;
import com.example.book_service.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;


import java.util.List;
import java.util.Map;


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

    private final WebClient webClient = WebClient.create("http://3.37.204.23:8000/ai/books"); // AI 서비스 URL

    @GetMapping
    public ResponseEntity<?> getRecommendations(@RequestHeader("Authorization") String token) {
        try {
            String cleanedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
            String username = jwtUtil.getUsername(cleanedToken);
//            int userId = userService.getUserIdByUsername(username);
            UserEntity user = userService.getUserByUsername(username);

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

//            if (response != null) {
//                UserRecommendation userRecommendation = new UserRecommendation();
//                userRecommendation.setUser(user);
//                List<Long> isbnNonfilter = response.getIsbn_nonfilter();
//                List<Long> isbnFilter = response.getIsbn_filter();
//
//
//                userRecommendationService.saveUserRecommendation(userRecommendation);
//
//                System.out.println("isbnNonfilter from AI service: " + isbnNonfilter);
//            }


            return ResponseEntity.ok(response);
        } catch (WebClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred");
        }
    }
}

//
//            JSONObject requestBody = new JSONObject();
//            requestBody.put("user_id", userId);
//            JSONArray genresArray = new JSONArray(favoriteGenres);
//            requestBody.put("genres", genresArray);
//
//            // AI 엔드포인트로 POST 요청 보내기
//            Map<String, Object> response = webClient.post()
//                    .uri("/books")
//                    .bodyValue(requestBody.toString()) // JSON 문자열로 변환하여 보냄
//                    .retrieve()
//                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
//                    .block();
//
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
//        }
//    }
//}



