package com.example.book_service.controller;

import com.example.book_service.dto.AIData.AIModelData;
import com.example.book_service.service.AI.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AIController {
    private final AIService aiService;

    @Autowired
    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/ai/data")
    public ResponseEntity<String> receiveDataFromAI(
            @RequestHeader(value = "User-name") String username,
            @RequestBody List<String> dataList) {

        // AIModelData 객체 생성
        AIModelData aiModelData = new AIModelData();
        aiModelData.setDataList(dataList);

        // AI 서비스를 통해 데이터베이스에 데이터 저장
        aiService.saveDataToDB(aiModelData, username);
        // 성공적으로 데이터를 받았음을 응답합니다.
        return new ResponseEntity<>("Data received successfully", HttpStatus.OK);
    }
}
