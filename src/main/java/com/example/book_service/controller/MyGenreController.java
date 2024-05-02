package com.example.book_service.controller;

import com.example.book_service.dto.GenrePreferencesDTO;
import com.example.book_service.dto.JoinDTO;
import com.example.book_service.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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

//    @PostMapping("/genre")
//    public String genreProcess(@RequestBody GenrePreferencesDTO genrePreferencesDTO){
//
//        GenreService.GenreProcess(GenrePreferencesDTO);
//        return "HTTP 200 (OK)";
//    }
}
