package com.example.book_service.FastAPI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {
    @GetMapping("/test/{message}")
    public String estMessage(@PathVariable String message) {
        return message;
    }
}
