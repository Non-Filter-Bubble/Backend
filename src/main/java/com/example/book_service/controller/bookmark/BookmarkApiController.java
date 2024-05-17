package com.example.book_service.controller.bookmark;


import com.example.book_service.JWT.JWTUtil;
import com.example.book_service.dto.bookmark.BookmarkResponseDto;
import com.example.book_service.dto.bookmark.BookmarkSaveRequestDto;
import com.example.book_service.service.UserService;
import com.example.book_service.service.bookmark.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="찜목록 API", description = "bookmark 관련 api입니다.")
@RequiredArgsConstructor
@RestController
public class BookmarkApiController {
    private final BookmarkService bookmarkService;
    private final JWTUtil jwtUtil;
    private final UserService userService;


    @Operation(summary = "찜한 책을 DB에 post",
        responses = {
                @ApiResponse(responseCode = "200", description = "등록된 bookmarkid를 반환"),
                @ApiResponse(responseCode = "403", description = "찜목록에 해당 책이 이미 있을 때, 중복 에러를 반환"),
        })
    @PostMapping("/user/like")
    public ResponseEntity<String> save(@RequestHeader(name = "Authorization") String token, @RequestBody BookmarkSaveRequestDto requestDto) throws Exception {
        String cleanedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        String username = jwtUtil.getUsername(cleanedToken);
        int userId = userService.getUserIdByUsername(username);
        requestDto.setUserId(userId); // 요청 DTO에 userId 설정
        return bookmarkService.save(requestDto);
    }

    @Operation(summary = "해당 user의 찜 목록 조회",
            responses =  {
                    @ApiResponse(responseCode = "200", description = "해당 user의 찜 목록을 반환"),
                    @ApiResponse(responseCode = "403", description = "해당 user가 없을 때, 에러를 반환")
            }
    )
    @GetMapping("/user/like")
    public ResponseEntity<List<BookmarkResponseDto>> findAllByToken(@RequestHeader(name = "Authorization") String token) throws Exception {
        String cleanedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        String username = jwtUtil.getUsername(cleanedToken);
        int userId = userService.getUserIdByUsername(username);

        List<BookmarkResponseDto> bookmarks = bookmarkService.findAllByUserId(userId);
        return ResponseEntity.ok(bookmarks);
    }

    @DeleteMapping("/user/like/{bookmarkid}")
    public ResponseEntity<String> deleteBookmark(@PathVariable("bookmarkid") Long bookmarkid) {
        // 북마크를 삭제하는 서비스 메서드 호출
        boolean isDeleted = bookmarkService.deleteBookmark(bookmarkid);

        if (isDeleted) {
            return ResponseEntity.ok("Bookmark with ID " + bookmarkid + " has been deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bookmark with ID " + bookmarkid + " not found.");
        }
    }
}
