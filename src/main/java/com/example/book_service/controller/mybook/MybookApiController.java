package com.example.book_service.controller.mybook;

import com.example.book_service.dto.mybook.MybookSaveRequestDto;
import com.example.book_service.dto.mybook.MybookUpdateRequestDto;
import com.example.book_service.service.mybook.MybookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;


@Tag(name = "북서랍 책 API", description = "mybook관련 api입니다.")
@RequiredArgsConstructor
@RestController
public class MybookApiController {
    private final MybookService mybookService;

    /* POST : mybook 추가 */
    @Operation(summary = "mybook 생성",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "mybook 생성 완료."
                    )
            })
    
    @PostMapping("/user/bookbox/mybook/post")
    public Long save(@RequestBody MybookSaveRequestDto requestDto) {
        return mybookService.save(requestDto);
    }

    /* PUT : 해당 mybookid 수정*/
    @Operation(summary = "해당 mybook 수정")
    @PutMapping("/user/bookbox/{mybookid}")
    public Long update(@PathVariable Long mybookid, @RequestBody MybookUpdateRequestDto requestDto) {
        return mybookService.update(mybookid, requestDto);
    }

    /* DELETE : 해당 mybookid 삭제*/
    @Operation(summary = "해당 mybook 삭제")
    @DeleteMapping("/user/bookbox/{mybookid}")
    public String delete(@PathVariable Long mybookid) {
        return mybookService.delete(mybookid);
    }
}
