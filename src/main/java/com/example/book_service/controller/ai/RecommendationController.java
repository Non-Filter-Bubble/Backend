package com.example.book_service.controller.ai;

import com.example.book_service.JWT.JWTUtil;

import com.example.book_service.entity.UserEntity;
import com.example.book_service.entity.UserRecommendation;
import com.example.book_service.service.AI.UserRecommendationService;
import com.example.book_service.service.GenreService;
import com.example.book_service.service.UserService;

import net.minidev.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.ParameterizedTypeReference;
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

    private final WebClient webClient = WebClient.create("http://3.37.204.233:8000/ai"); // AI 서비스 URL

    @GetMapping
    public ResponseEntity<?> getRecommendations(@RequestHeader("Authorization") String token) {
        try {
            String cleanedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
            String username = jwtUtil.getUsername(cleanedToken);
//            int userId = userService.getUserIdByUsername(username);
            UserEntity user = userService.getUserByUsername(username);

            List<String> favoriteGenres = genreService.getFavoriteGenresByUserId(user.getUserid());

            System.out.println("userId : " + user);
            System.out.println("username : " + username);
            System.out.println("favoriteGenres : " + favoriteGenres);


            // JSON 요청의 형식 확인
            JSONObject requestBody = new JSONObject();
            requestBody.put("user_id", user.getUserid());
            requestBody.put("genres", new JSONArray(favoriteGenres));
            System.out.println("JSON 요청: " + requestBody.toString());


            // AI 엔드포인트로 POST 요청 보내기
            System.out.println("Request sent to AI service");
            Map<String, Object> response = webClient.post()
                    .uri("/books")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                    })
                    .block();

            System.out.println("Response from AI service: " + response);
            if (response != null) {
                UserRecommendation userRecommendation = new UserRecommendation();
                userRecommendation.setUser(user);
                userRecommendation.setIsbnNonFilter((List<Long>) response.get("isbn-nonfilter"));
                userRecommendation.setIsbnFilter((List<Long>) response.get("isbn-filter"));

                userRecommendationService.saveUserRecommendation(userRecommendation);
            }

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



