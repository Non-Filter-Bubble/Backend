package com.example.book_service.controller.bookmark;


import com.example.book_service.dto.bookmark.BookmarkResponseDto;
import com.example.book_service.dto.bookmark.BookmarkSaveRequestDto;
import com.example.book_service.service.bookmark.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="찜목록 API", description = "bookmark 관련 api입니다.")
@RequiredArgsConstructor
@RestController
public class BookmarkApiController {
    private final BookmarkService bookmarkService;


    @Operation(summary = "찜한 책을 DB에 post",
        responses = {
                @ApiResponse(responseCode = "200", description = "등록된 bookmarkid를 반환"),
                @ApiResponse(responseCode = "403", description = "찜목록에 해당 책이 이미 있을 때, 중복 에러를 반환"),
        })
    @PostMapping("/user/like")
    public Long save(@RequestBody BookmarkSaveRequestDto requestDto) {
        return bookmarkService.save(requestDto);
    }


    @Operation(summary = "해당 user의 찜 목록 조회",
            responses =  {
                    @ApiResponse(responseCode = "200", description = "해당 user의 찜 목록을 반환"),
                    @ApiResponse(responseCode = "403", description = "해당 user가 없을 때, 에러를 반환")
            }
    )
    @GetMapping("/user/like/{userid}")
    public List<BookmarkResponseDto> findAllByUserId(@PathVariable int userid) {
        return bookmarkService.findAllByUserId(userid);
    }

}
