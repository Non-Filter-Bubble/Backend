package com.example.book_service.controller.mybook;

import com.example.book_service.JWT.JWTUtil;
import com.example.book_service.domain.mybook.MybookEntity;
import com.example.book_service.dto.mybook.MybookSaveRequestDto;
import com.example.book_service.dto.mybook.MybookUpdateRequestDto;
import com.example.book_service.entity.UserEntity;
import com.example.book_service.service.UserService;
import com.example.book_service.service.mybook.MybookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "북서랍 책 API", description = "mybook관련 api입니다.")
@RequiredArgsConstructor
@RestController
public class MybookApiController {
    private final MybookService mybookService;
    @Autowired
    private UserService userService;
    private final JWTUtil jwtUtil;

    /* POST : mybook 추가 */
    @Operation(summary = "mybook 생성",
            responses = {
                    @ApiResponse(responseCode = "200", description = "등록된 mybookid를 반환")
            })

    @PostMapping("/user/bookbox/mybook/post")
    public Long save(@RequestBody MybookSaveRequestDto requestDto) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int userId = userService.getUserIdByUsername(username);
        // 요청 DTO에 사용자 ID 설정
        requestDto.setUserid(userId);
        return mybookService.save(requestDto);
    }

    /* PUT : 해당 mybookid 수정*/
    @Operation(summary = "해당 mybook 수정",
            responses = {
                    @ApiResponse(responseCode = "200", description = "수정 완료된 mybookid를 반환")
            })
    @PutMapping("/user/bookbox/{mybookid}")
    public Long update(@PathVariable("mybookid") Long mybookid, @RequestBody MybookUpdateRequestDto requestDto) {
        return mybookService.update(mybookid, requestDto);
    }

    /* DELETE : 해당 mybookid 삭제*/
    @Operation(summary = "해당 mybook 삭제",
            responses = {
                    @ApiResponse(responseCode = "200", description = "\"delete complete.\"를 반환")
            })
    @DeleteMapping("/user/bookbox/{mybookid}")
    public String delete(@PathVariable("mybookid") Long mybookid) {
        return mybookService.delete(mybookid);
    }

    @GetMapping("/user/bookbox/mybook")
    public ResponseEntity<?> verifyGenre(@RequestHeader("Authorization") String token) throws Exception {
        String cleanedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        String username = jwtUtil.getUsername(cleanedToken);
        int userId = userService.getUserIdByUsername(username);
        List<MybookEntity> books = mybookService.getBooksByUserid(userId);
        return ResponseEntity.ok(books);
    }


}