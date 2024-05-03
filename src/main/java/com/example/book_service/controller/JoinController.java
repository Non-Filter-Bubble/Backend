package com.example.book_service.controller;

import com.example.book_service.dto.JoinDTO;
import com.example.book_service.service.JoinService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class JoinController{
    private final JoinService joinService;

    public JoinController(JoinService joinService){

        this.joinService = joinService;
    }

    @GetMapping("/join")
    public String joinPage(){
        return "join";
    }


@PostMapping(path = "/join", consumes = "application/json")  // JSON 데이터를 받아 처리
    public ResponseEntity<?> joinProcess(@RequestBody JoinDTO joinDTO) {
        try {
            // joinService에서 JWT 토큰을 반환받음
            String token = joinService.joinProcess(joinDTO);

            // HttpHeaders 객체를 생성하여 토큰을 "Authorization" 헤더에 추가
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);

            // ResponseEntity를 사용하여 헤더와 함께 "User registered successfully" 메시지를 응답
            return ResponseEntity.ok().headers(headers).body("User registered successfully");
        } catch (IllegalStateException e) {
            // 예외가 발생한 경우, 적절한 HTTP 상태 코드와 에러 메시지를 반환
            return ResponseEntity.badRequest().body(e.getMessage());
        }
}

}