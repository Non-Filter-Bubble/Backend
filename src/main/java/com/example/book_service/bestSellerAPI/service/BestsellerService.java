package com.example.book_service.bestSellerAPI.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Map;

@Service
public class BestsellerService {
    private final WebClient webClient;

    public BestsellerService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://www.aladin.co.kr/ttb/api/ItemList.aspx").build();
    }

    public Mono<Map<String, Object>> searchBestsellers(String categoryId, int year, int month, int week) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("ttbkey", "ttb2001blue011415002")
                        .queryParam("QueryType", "Bestseller")
                        .queryParam("MaxResults", 20)
                        .queryParam("start", 1)
                        .queryParam("SearchTarget", "Book")
                        .queryParam("Cover", "Big")
                        .queryParam("Output", "JS")
                        .queryParam("Version", "20131101")
                        .queryParam("Year", year)
                        .queryParam("Month", month)
                        .queryParam("Week", week)
                        .queryParam("CategoryId", categoryId)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
    }
}
