//package com.example.book_service.searchLibraryAPI.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.logging.Logger;
//
//@Configuration
//public class RegionConfig {
//
//    private static final Logger logger = Logger.getLogger(RegionConfig.class.getName());
//
//    @Bean
//    public Map<String, Integer> regionCodeMap() throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            return objectMapper.readValue(new ClassPathResource("region_code.json").getFile(), Map.class);
//        } catch (IOException e) {
//            logger.severe("Failed to read region_code.json: " + e.getMessage());
//            throw e;
//        }
//    }
//}
