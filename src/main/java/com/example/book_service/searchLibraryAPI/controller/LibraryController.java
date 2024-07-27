package com.example.book_service.searchLibraryAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class LibraryController {
    @Autowired
    private LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/searchLibraryByBook")
    public Mono<String> getExternalApiResponse(@RequestParam(name="isbn") long isbn, @RequestParam(name = "region") long region) {
        return libraryService.searchLibraryByBook(isbn, region);
    }
}
