package com.example.book_service.controller;

import com.example.book_service.JWT.JWTUtil;
import com.example.book_service.dto.GenrePreferencesDTO;
import com.example.book_service.dto.JoinDTO;
import com.example.book_service.service.GenreService;
import com.example.book_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class MyGenreController {

    @Autowired
    private RestTemplate restTemplate;

    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final GenreService genreService;

    @Autowired
    public MyGenreController(JWTUtil jwtUtil, UserService userService, GenreService genreService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.genreService = genreService;
    }

    @PostMapping("/genre")
    @ResponseBody
    public void handleGenre(@RequestBody GenrePreferencesDTO genrePreferencesDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // 현재 로그인한 사용자의 이름 (또는 ID)을 가져옴
        genreService.GenreProcess(genrePreferencesDTO, username);  // 사용자 ID 대신 사용자 이름을 전달할 수 있음

//        // AI 서비스로 데이터 전송
//        String aiServiceUrl = "http://43.200.64.238:8000/";  // AI 서비스의 엔드포인트 URL
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<GenrePreferencesDTO> request = new HttpEntity<>(genrePreferencesDTO, headers);
//
//        ResponseEntity<String> response = restTemplate.postForEntity(aiServiceUrl, request, String.class);
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            System.out.println("Data sent to AI service successfully");
//        } else {
//            System.out.println("Failed to send data to AI service");
//        }
    }

    @GetMapping("/verify-genre")
    public ResponseEntity<?> verifyGenre(@RequestHeader("Authorization") String token) {
        String cleanedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        String username = jwtUtil.getUsername(cleanedToken);

        try {
            int userId = userService.getUserIdByUsername(username);
            boolean hasGenre = genreService.checkUserGenreExists(userId);
            if (hasGenre) {
                return ResponseEntity.ok("Genre exists for this user.");
            } else {
                return ResponseEntity.badRequest().body("No genre found for this user.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}