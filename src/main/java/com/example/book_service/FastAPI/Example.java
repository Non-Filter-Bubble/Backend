package com.example.book_service.FastAPI;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class Example {
    public static void main(String[] args) {
        // WebClient를 생성
        WebClient webClient = WebClient.create();
        String fastapiUrl = "http://fastapi-server:8000/echo"; // FastAPI 서버의 엔드포인트 URL

        // 보낼 문자열
        String message = "Hello from Spring!";

        // WebClient를 사용하여 POST 요청으로 문자열을 FastAPI로 보냄
        String response = webClient.post()
                .uri(fastapiUrl)
                .body(BodyInserters.fromValue(message))
                .retrieve()
                .bodyToMono(String.class)
                .block(); // 블로킹 호출로 응답을 기다림

        // FastAPI로부터의 응답 처리
        System.out.println("Response from FastAPI: " + response);
    }
}
