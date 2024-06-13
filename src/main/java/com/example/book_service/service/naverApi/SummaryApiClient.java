package com.example.book_service.service.naverApi;

import com.example.book_service.dto.NaverApi.NaverResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    public Mono<NaverResponseDto> requestBookByIsbn(String isbn) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v1/search/book_adv.json")
                        .queryParam("d_isbn", isbn)
                        .build())
                .retrieve()
                .bodyToMono(NaverResponseDto.class);
    }
}