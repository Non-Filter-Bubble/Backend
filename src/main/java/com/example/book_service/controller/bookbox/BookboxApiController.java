package com.example.book_service.controller.bookbox;

import com.example.book_service.domain.mybook.MybookRepository;
import com.example.book_service.dto.bookbox.BookboxResponseDto;
import com.example.book_service.dto.bookbox.BookboxSaveRequestDto;
import com.example.book_service.dto.mybook.MybookUpdateRequestDto;
import com.example.book_service.service.bookbox.BookboxService;
import com.example.book_service.service.mybook.MybookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    /* GET : 해당 user의 북서랍 목록 조회 */
//    @GetMapping("/user/bookbox/userid/{userid}")
//    public List<BookboxResponseDto> findAllByUserId(@PathVariable int userid) {
//        return bookboxService.findAllByUserId(userid);
//    }

    /* GET : 해당 user의 북서랍에 속한 모든 책을 장르별로 그룹화하여 조회 */
    @GetMapping("/user/bookbox/all/{userid}")
    public Map<String, List<Long>> findMybookIdsByUserId(@PathVariable int userid) {
        return bookboxService.findMybooksByUserIdAndGroupByGenre(userid);
    }

}
