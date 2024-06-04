package com.example.book_service.controller.ai;

import com.example.book_service.JWT.JWTUtil;
import com.example.book_service.entity.UserEntity;
import com.example.book_service.service.GenreService;
import com.example.book_service.service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.book_service.entity.GenreEntity;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recommend")
public class RecommendationController {
    @Autowired
    private GenreService genreService;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    private final WebClient webClient = WebClient.create("http://ai"); // AI 서비스 URL

    @GetMapping
    public ResponseEntity<?> getRecommendations(@RequestHeader("Authorization") String token) {
        try {
            String cleanedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
            String username = jwtUtil.getUsername(cleanedToken);
            int userId = userService.getUserIdByUsername(username);

            List<String> favoriteGenres = genreService.getFavoriteGenresByUserId(userId);

            System.out.println("userId : " + userId);
            System.out.println("username : " + username);
            System.out.println("favoriteGenres : " + favoriteGenres);

            return ResponseEntity.ok(favoriteGenres);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred");
        }
    }
}


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



