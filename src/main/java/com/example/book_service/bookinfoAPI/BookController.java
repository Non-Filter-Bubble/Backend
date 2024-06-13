package com.example.book_service.bookinfoAPI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
    public class BookController {
//        private static final Logger logger = LoggerFactory.getLogger(BookController.class);
        private final BookLoader bookLoader;
        private final BookClient bookClient;

        public BookController(BookLoader bookLoader,BookClient bookClient) {

            this.bookLoader = bookLoader;
            this.bookClient = bookClient;
        }

        @GetMapping("/load-books")
        public ResponseEntity<BookInfoEntity> loadBookByIsbn(@RequestParam("isbn") Long isbn) {

                // IOException 제거
                List<BookInfoEntity> books = bookLoader.loadBooks("/share_best_books_data.json");
                for (BookInfoEntity book : books) {
                    // ISBN을 문자열로 변환하여 비교
                    if (String.valueOf(book.getIsbnThirteenNo()).equals(String.valueOf(isbn))) {
                        return ResponseEntity.ok(book);
                    }
                }
                return ResponseEntity.notFound().build();
        }
        @GetMapping("/search-books")
        public Mono<ApiResponse> searchBooks(
                @RequestParam("type") String type,
                @RequestParam("value") String value) {
            return bookClient.searchBooks(type, value);
        }

    }



