package com.example.book_service.service.naverApi;

import com.example.book_service.dto.NaverApi.NaverResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class SummaryApiClient {

    private final WebClient webClient;

    public SummaryApiClient(WebClient.Builder webClientBuilder,
                            @Value("${naver.api.url}") String apiUrl,
                            @Value("${naver.client.id}") String clientId,
                            @Value("${naver.client.secret}") String clientSecret) {
        this.webClient = webClientBuilder.baseUrl(apiUrl)
                .defaultHeader("X-Naver-Client-Id", clientId)
                .defaultHeader("X-Naver-Client-Secret", clientSecret)
                .build();
    }

    public Mono<NaverResponseDto> requestBookByTitle(String value) {
        String encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8);
        System.out.println("Searching for title: " + encodedValue); // 인코딩된 제목 출력
        String apiUrl = "/v1/search/book.json?query=" + encodedValue + "&display=10&start=1&sort=sim";
        return this.webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToMono(NaverResponseDto.class)
                .doOnNext(response -> {
                    System.out.println("API Response: " + response); // 응답 출력
                });
    }

    public Mono<NaverResponseDto> requestBookByIsbn(String value) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v1/search/book_adv.json")
                        .queryParam("d_isbn", value)
                        .build())
                .retrieve()
                .bodyToMono(NaverResponseDto.class);
    }

}