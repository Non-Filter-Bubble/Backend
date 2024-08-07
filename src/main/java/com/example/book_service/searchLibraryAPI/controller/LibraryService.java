package com.example.book_service.searchLibraryAPI.controller;

import com.example.book_service.searchLibraryAPI.dto.LibraryResponseDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class LibraryService {

    private final WebClient webClient;
    private final String apiKey;
    private final String apiUrl; // 기본 URL을 저장할 변수
    private final XmlMapper xmlMapper;

    public LibraryService(@Value("${library.api.url}") String apiUrl,
                          @Value("${library.api.key}") String apiKey) {
        this.apiUrl = apiUrl; // 기본 URL 초기화
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl) // 기본 URL 설정
                .defaultHeader(HttpHeaders.ACCEPT, "application/xml") // 기본 Accept 헤더 설정
                .build();
        this.apiKey = apiKey;
        this.xmlMapper = new XmlMapper(); // XmlMapper 초기화
    }

    public Mono<LibraryResponseDTO> searchLibraryByBook(long isbn, long region) {
        try {
            // UriComponentsBuilder를 사용하여 URL을 직접 생성하고 로그로 출력
            String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                    .path("/api/libSrchByBook")
                    .queryParam("authKey", apiKey)
                    .queryParam("isbn", isbn)
                    .queryParam("region", region)
                    .queryParam("format", "xml")
                    .toUriString();
            System.out.println("Generated URL: " + url); // 로그로 URL 출력

            return this.webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/libSrchByBook")
                            .queryParam("authKey", apiKey)
                            .queryParam("isbn", isbn)
                            .queryParam("region", region)
                            .queryParam("format", "xml")
                            .build()) // URI 빌더로 URL 설정
                    .header(HttpHeaders.ACCEPT, "application/xml") // 요청마다 Accept 헤더 추가
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            clientResponse -> clientResponse.bodyToMono(String.class)
                                    .flatMap(body -> Mono.error(new RuntimeException("API call failed with status: " + clientResponse.statusCode() + " and response: " + body)))
                    )
                    .bodyToMono(String.class)
                    .map(response -> {
                        try {
                            // XML을 JSON으로 변환
                            JsonNode jsonNode = xmlMapper.readTree(response);
                            // JSON을 LibraryResponseDTO로 변환
                            return xmlMapper.treeToValue(jsonNode, LibraryResponseDTO.class);
                        } catch (Exception e) {
                            throw new RuntimeException("Failed to parse XML response", e);
                        }
                    })
                    .doOnNext(response -> System.out.println("Received response: " + response))
                    .doOnError(error -> {
                        // 에러 메시지 출력
                        System.err.println("Error occurred: " + error.getMessage());
                        // 전체 스택 트레이스 출력
                        error.printStackTrace();
                    });
        } catch (WebClientResponseException e) {
            // 적절한 예외 처리
            System.out.println("API 호출 실패: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw new RuntimeException("API 호출에 실패했습니다.");
        }
    }
}
