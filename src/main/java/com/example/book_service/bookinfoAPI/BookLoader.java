package com.example.book_service.bookinfoAPI;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class BookLoader {
//    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    public List<BookInfoEntity> loadBooks(String filePath) throws IOException {
        File file = new ClassPathResource(filePath).getFile();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, new TypeReference<List<BookInfoEntity>>() {});
    }
}