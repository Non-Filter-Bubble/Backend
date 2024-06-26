package com.example.book_service.controller.booksummary;


//import com.example.book_service.bookinfoAPI.ApiResponse;
import com.example.book_service.dto.NaverApi.NaverResponseDto;
import com.example.book_service.service.naverApi.SummaryApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
public class NaverApiController {

    private final SummaryApiClient summaryApiClient;

    @Autowired
    public NaverApiController(SummaryApiClient summaryApiClient) {
        this.summaryApiClient = summaryApiClient;
    }

//    @GetMapping("/book/summary/{isbn}")
//    public NaverResponseDto getBookSummary(@PathVariable("isbn") String isbn) {
//        NaverResponseDto response = summaryApiClient.requestBookByIsbn(isbn).block();
//        System.out.println("Received response: " + response);
//        return response;
//    }

    @GetMapping("/search-NaverBooks")
    public Mono<NaverResponseDto> searchBooks(
            @RequestParam("type") String type,
            @RequestParam("value") String value) {
        if ("title".equals(type)) {
            return summaryApiClient.requestBookByTitle(value);
        } else if ("isbn".equals(type)) {
            return summaryApiClient.requestBookByIsbn(value);
        }
        return Mono.empty(); // 잘못된 타입의 경우 빈 응답 반환
    }
}

