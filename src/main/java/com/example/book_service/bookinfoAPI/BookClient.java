package com.example.book_service.bookinfoAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class BookClient {
    private WebClient webClient;
    private final String apiKey;

    public BookClient(@Value("${api.base.url}") String baseUrl,
                      @Value("${api.key}") String apiKey) {
        this.apiKey = apiKey;
        // WebClient 초기화
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey) // 필요하다면 헤더에 API 키 추가
                .build();
    }

    // 책 정보를 조회하는 메소드, API 키를 URL의 쿼리 파라미터로 포함
    public Mono<ApiResponse> searchBooks(String type, String value) {
        ObjectMapper mapper = new ObjectMapper(); // Jackson의 ObjectMapper 인스턴스 생성
        return this.webClient.get()
                .uri(uriBuilder -> {
                    URI uri = uriBuilder
                            .queryParam("cert_key", apiKey) // API 키를 쿼리 파라미터로 추가
                            .queryParam("result_style", "json")
                            .queryParam("page_no", 1)
                            .queryParam("page_size", 10)
                            .queryParam("form", "종이책")
                            .queryParam(type, value)
                            .build();
                    return uri;
                })
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .doOnNext(apiResponse -> {
                    try {
                        String jsonResponse = mapper.writeValueAsString(apiResponse);
                        System.out.println("Received JSON response: " + jsonResponse);
                    } catch (Exception e) {
                        System.err.println("Error processing response: " + e.getMessage());
                    }
                });
    }
}


