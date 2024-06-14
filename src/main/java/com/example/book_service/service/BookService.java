package com.example.book_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
public class BookService {

    public Map<String, Object> getIsbnList() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getResourceAsStream("/recommend_isbn.json");
        return mapper.readValue(is, Map.class);
    }
}