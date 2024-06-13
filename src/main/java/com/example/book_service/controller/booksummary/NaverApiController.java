package com.example.book_service.controller.booksummary;


import com.example.book_service.dto.NaverApi.NaverResponseDto;
import com.example.book_service.service.naverApi.SummaryApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NaverApiController {

    private final SummaryApiClient summaryApiClient;

    @Autowired
    public NaverApiController(SummaryApiClient summaryApiClient) {
        this.summaryApiClient = summaryApiClient;
    }

    @GetMapping("/book/summary/{isbn}")
    public NaverResponseDto getBookSummary(@PathVariable("isbn") String isbn) {
        NaverResponseDto response = summaryApiClient.requestBookByIsbn(isbn).block();
        System.out.println("Received response: " + response);
        return response;
    }
}
