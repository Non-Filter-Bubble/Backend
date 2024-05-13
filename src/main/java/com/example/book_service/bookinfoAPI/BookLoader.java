package com.example.book_service.bookinfoAPI;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookLoader {
    public List<BookInfoEntity> loadBooks(String filePath) {
        List<BookInfoEntity> books = new ArrayList<>();
        try {
            // 파일의 절대 경로를 가져옴
            Resource resource = new ClassPathResource(filePath);
            InputStream inputStream = resource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            // 파일 내용을 문자열로 읽음
            String jsonContent = reader.lines().collect(Collectors.joining());
            ObjectMapper mapper = new ObjectMapper();
            // JSON 데이터를 BookInfoEntity 리스트로 변환
            books = mapper.readValue(jsonContent, new TypeReference<List<BookInfoEntity>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            // 파일 로드 실패 시 처리 로직
        }
        return books;
    }
}