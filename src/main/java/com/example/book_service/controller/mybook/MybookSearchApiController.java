package com.example.book_service.controller.mybook;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "북서랍 책 검색 API", description = "도서명, 저자, 출판사를 검색하는 api입니다.")
@RequiredArgsConstructor
@RestController
public class MybookSearchApiController {

//
//    /* GET : 도서명 검색*/
//    @Operation(summary = "도서명 검색")
//    @GetMapping("/mybook/book_name/{bookname}")
//    public BookResponseDto findByBookname(@PathVariable String bookname) {
//
//        return ;
//    }
//
//    /* GET : 저자 검색*/
//    @Operation(summary = "저자 검색")
//    @GetMapping("/mybook/author/{author}")
//    public BookResponseDto findByAuthor(@PathVariable String author) {
//
//        return ;
//    }
//
//
//    /* GET : 출판사 검색*/
//    @Operation(summary = "출판사 검색")
//    @GetMapping("/mybook/publisher/{publisher}")
//    public BookResponseDto findByPublisher(@PathVariable String publisher) {
//
//        return ;
//    }

}
