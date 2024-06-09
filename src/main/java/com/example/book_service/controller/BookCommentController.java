package com.example.book_service.controller;

import com.example.book_service.service.mybook.MybookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookCommentController {

    private final MybookService mybookService;

    @Autowired
    public BookCommentController(MybookService mybookService) {
        this.mybookService = mybookService;
    }

    //user들이 등록한 한줄평 불러오기
    @GetMapping("/comment")
    public List<String> getCommentsByIsbn(@RequestParam(value = "isbn") Long isbn) {
        return mybookService.getCommentsByIsbn(isbn);
    }
}
