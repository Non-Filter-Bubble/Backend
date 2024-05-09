package com.example.book_service.controller;

import com.example.book_service.dto.GenrePreferencesDTO;
import com.example.book_service.dto.JoinDTO;
import com.example.book_service.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
public class MyGenreController {

    private final GenreService genreService;

    @Autowired
    public MyGenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PostMapping("/genre")
    public void handleGenre(@RequestBody GenrePreferencesDTO genrePreferencesDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // 현재 로그인한 사용자의 이름 (또는 ID)을 가져옴
        genreService.GenreProcess(genrePreferencesDTO, username);  // 사용자 ID 대신 사용자 이름을 전달할 수 있음
    }

    @GetMapping("/genre/check-database")
    public ResponseEntity<String> checkDatabaseContent() {
        boolean isEmpty = genreService.isDatabaseEmpty();
        if (isEmpty) {
            return ResponseEntity.ok("Database is not empty");
        } else {
            return ResponseEntity.ok("Database is empty");
        }
    }
}
