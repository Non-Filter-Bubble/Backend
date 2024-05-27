package com.example.book_service.config;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorsController {
    @CrossOrigin(origins = {"http://43.203.38.124", "http://43.200.64.238"})
    @GetMapping("/example")
    public String example() {
        return "Hello from Spring Boot!";
    }
}
