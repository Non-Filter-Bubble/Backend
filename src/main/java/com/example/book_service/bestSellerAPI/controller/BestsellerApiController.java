package com.example.book_service.bestSellerAPI.controller;

import com.example.book_service.bestSellerAPI.model.BestsellerDetails;
import com.example.book_service.bestSellerAPI.service.BestsellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Tag(name="베스트셀러 API", description = "장르별 베스트셀러 목록을 조회하는 api입니다.")
@RequiredArgsConstructor
@RestController
public class BestsellerApiController {
    private final BestsellerService bestsellerService;

    // 장르별 카테고리 ID를 매핑하는 정적 맵
    private static final Map<String, List<String>> genreToCategoryIdsMap;

    static {
        genreToCategoryIdsMap = new HashMap<>();
        genreToCategoryIdsMap.put("소설", List.of("50930", "50935", "50931", "50932", "50933", "50929", "50926", "50953", "50928"));
        genreToCategoryIdsMap.put("자연과학", List.of("987"));
        genreToCategoryIdsMap.put("인문", List.of("656"));
        genreToCategoryIdsMap.put("시/에세이", List.of("55889", "50940"));
        genreToCategoryIdsMap.put("자기계발", List.of("336"));
        genreToCategoryIdsMap.put("경제/경영", List.of("170"));
    }

    /**
     * 베스트셀러 목록을 가져오는 엔드포인트
     * @param genre 장르 이름
     * @return 주어진 장르에 해당하는 베스트셀러 목록
     */

    @Operation(summary = "각 장르에 대한 베스트셀러 목록 조회")
    @Parameter(name="genre", description = "책의 장르")
    @GetMapping("/bestseller/{genre}")
    public Mono<List<BestsellerDetails>> getBooks(@PathVariable String genre) {
        // 현재 날짜 정보를 가져와 연도, 월, 주차를 계산
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        int month = date.getMonthValue();
        int week = 1;

        // 주어진 장르에 해당하는 카테고리 ID 목록을 가져옴
        List<String> categoryIds = genreToCategoryIdsMap.getOrDefault(genre, List.of());

        // 각 카테고리 ID에 대해 비동기로 베스트셀러 데이터를 조회하고 결과를 합침
        Flux<Map<String, Object>> resultFlux = Flux.fromIterable(categoryIds)
                .parallel()
                .runOn(Schedulers.parallel())
                .flatMap(categoryId -> bestsellerService.searchBestsellers(categoryId, year, month, week))
                .sequential();

        // 결과를 수집하고 병합한 뒤, 판매 포인트(salesPoint) 기준으로 정렬하여 상위 20개 항목만 반환
        return resultFlux.collectList().map(this::mergeResults)
                .map(books -> books.stream()
                        .sorted(Comparator.comparingInt(book -> (Integer) ((Map<String, Object>) book).getOrDefault("salesPoint", 0)).reversed())
                        .map(this::mapToBestsellerDetails)
                        .limit(20)
                        .collect(Collectors.toList())
                );
    }

    /**
     * 여러 API 호출 결과를 병합하는 메서드
     * @param resultList API 호출 결과 목록
     * @return 병합된 결과 목록
     */
    private List<Map<String, Object>> mergeResults(List<Map<String, Object>> resultList) {
        List<Map<String, Object>> books = new ArrayList<>();
        Set<String> seenIsbns = new HashSet<>(); // 중복 제거를 위한 ISBN 집합

        for (Map<String, Object> result : resultList) {
            // 각 결과에서 'item' 키 아래의 책 목록을 가져옴
            List<Map<String, Object>> categoryBooks = (List<Map<String, Object>>) result.getOrDefault("item", Collections.emptyList());
            for (Map<String, Object> book : categoryBooks) {
                String isbn = (String) book.get("isbn13"); // ISBN13 값 가져오기
                if (!seenIsbns.contains(isbn)) { // 중복 ISBN인지 확인
                    seenIsbns.add(isbn); // ISBN을 집합에 추가
                    books.add(book); // 중복이 아닌 경우 책 목록에 추가
                }
            }
        }

        return books; // 병합된 결과 반환
    }

    /**
     * 책의 데이터를 BestsellerDetails 객체로 변환하는 메서드
     * @param book 책 데이터 맵
     * @return BestsellerDetails 객체
     */
    private BestsellerDetails mapToBestsellerDetails(Map<String, Object> book) {
        BestsellerDetails details = new BestsellerDetails();
        details.setIsbn((String) book.get("isbn13")); // ISBN13 설정
        details.setTitle((String) book.get("title")); // 제목 설정
        details.setAuthor((String) book.get("author")); // 저자 설정
        details.setPublisher((String) book.get("publisher")); // 출판사 설정
        details.setCover((String) book.get("cover")); // 표지 이미지 URL 설정
        return details; // 변환된 객체 반환
    }
}
