package com.example.book_service.bookinfoAPI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

    @RestController
    public class BookController {
        private final BookLoader bookLoader;


        public BookController(BookLoader bookLoader) {
            this.bookLoader = bookLoader;
        }

        @GetMapping("/load-books")
        public ResponseEntity<List<BookInfoEntity>> loadBooks() {
            try {
                List<BookInfoEntity> books = bookLoader.loadBooks("share_best_books_data.json");
                return ResponseEntity.ok(books);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }



