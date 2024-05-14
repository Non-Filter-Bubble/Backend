package com.example.book_service.controller.bookbox;

import com.example.book_service.JWT.JWTUtil;
import com.example.book_service.domain.bookbox.BookboxEntity;
import com.example.book_service.domain.mybook.MybookRepository;
import com.example.book_service.dto.bookbox.BookboxResponseDto;
import com.example.book_service.dto.bookbox.BookboxSaveRequestDto;
import com.example.book_service.dto.mybook.MybookUpdateRequestDto;
import com.example.book_service.service.UserService;
import com.example.book_service.service.bookbox.BookboxService;
import com.example.book_service.service.mybook.MybookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name="북서랍 API", description = "bookbox관련 api입니다.")
@RequiredArgsConstructor
@RestController
public class BookboxApiController {
    private final BookboxService bookboxService;
    private final JWTUtil jwtUtil;
    private final UserService userService;


    /* POST : 북서랍 생성 */
    @Operation(summary = "북서랍 생성",
            responses = {
                    @ApiResponse(responseCode = "200", description = "등록된 bookboxid를 반환"),
            })
    @PostMapping("user/bookbox/post")
    public Long save(@RequestBody BookboxSaveRequestDto requestDto) {
        return bookboxService.save(requestDto);
    }

    /* GET : 북서랍 조회 */// bookboxid로 조회하는 게... 프론트쪽 사용자는 해당id를 알수 없으니까 userid로 조회하는게 맞는 것 같아서 그걸로 수정했어!
//    @Operation(summary = "북서랍 조회",
//            responses = {
//                    @ApiResponse(responseCode = "200", description = "해당 북서랍 조회"),
//            })
////    @GetMapping("/user/bookbox/{bookboxid}")
////    public BookboxResponseDto findById(@PathVariable Long bookboxid) {
////        return bookboxService.findById(bookboxid);
////    }

    /* GET : 해당 user의 북서랍 목록 조회 */
//    @GetMapping("/user/bookbox/userid/{userid}")
//    public List<BookboxResponseDto> findAllByUserId(@PathVariable int userid) {
//        return bookboxService.findAllByUserId(userid);
//    }

    /* GET : 해당 user의 북서랍에 속한 모든 책을 장르별로 그룹화하여 조회 */
    @Operation(summary = "해당 user의 북서랍에 속한 모든 책들을 장르별로 그룹화하여 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "해당 user의 북서랍에 속한 모든 책들을 장르별로 그룹화하여 반환"),
            })
    @GetMapping("/user/bookbox/all")
    public Map<String, List<Long>> findMybookIdsByUserId(@PathVariable("userid") int userid) {
        return bookboxService.findMybooksByUserIdAndGroupByGenre(userid);
    }
//    public ResponseEntity<?> verifyGenre(@RequestHeader("Authorization") String token) {
//        String cleanedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
//        String username = jwtUtil.getUsername(cleanedToken);




    @GetMapping("/user/bookboxid")
    public ResponseEntity<?> verifyGenre(@RequestHeader("Authorization") String token) throws Exception {
        String cleanedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        String username = jwtUtil.getUsername(cleanedToken);
        int userId = userService.getUserIdByUsername(username);

        List<BookboxResponseDto> bookBoxes = bookboxService.findAllByUserId(userId);

        List<Map<String, Object>> bookBoxInfoList = new ArrayList<>();
        for (BookboxResponseDto bookBox : bookBoxes) {
            Map<String, Object> bookBoxInfo = new HashMap<>();
            bookBoxInfo.put("bookboxid", bookBox.getBookboxid());
            bookBoxInfo.put("genre", bookBox.getGenre());
            bookBoxInfoList.add(bookBoxInfo);
        }
        return ResponseEntity.ok(bookBoxInfoList);
    }

}
