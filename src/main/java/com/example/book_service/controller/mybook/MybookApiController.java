package com.example.book_service.controller.mybook;

import com.example.book_service.dto.mybook.MybookSaveRequestDto;
import com.example.book_service.dto.mybook.MybookUpdateRequestDto;
import com.example.book_service.service.mybook.MybookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MybookApiController {
    private final MybookService mybookService;

    /* POST : mybook 추가 */
    @PostMapping("/user/bookbox/mybook/post")
    public Long save(@RequestBody MybookSaveRequestDto requestDto) {
        return mybookService.save(requestDto);
    }

    /* PUT : 해당 mybookid 수정*/
    @PutMapping("/user/bookbox/{mybookid}")
    public Long update(@PathVariable Long mybookid, @RequestBody MybookUpdateRequestDto requestDto) {
        return mybookService.update(mybookid, requestDto);
    }

    /* DELETE : 해당 mybookid 삭제*/
    @DeleteMapping("/user/bookbox/{mybookid}")
    public String delete(@PathVariable Long mybookid) {
        return mybookService.delete(mybookid);
    }
}
