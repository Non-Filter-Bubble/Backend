package com.example.book_service.controller.bookbox;

import com.example.book_service.dto.bookbox.BookboxResponseDto;
import com.example.book_service.dto.bookbox.BookboxSaveRequestDto;
import com.example.book_service.service.bookbox.BookboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BookboxApiController {
    private final BookboxService bookboxService;

    /* POST : 북서랍 생성 */
    @PostMapping("user/bookbox/post")
    public Long save(@RequestBody BookboxSaveRequestDto requestDto) {
        return bookboxService.save(requestDto);
    }

    /* GET : 북서랍 조회 */
    @GetMapping("/user/bookbox/{bookboxid}")
    public BookboxResponseDto findById(@PathVariable Long bookboxid) {
        return bookboxService.findById(bookboxid);
    }
}
