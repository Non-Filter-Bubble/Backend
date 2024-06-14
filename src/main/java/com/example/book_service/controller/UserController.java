package com.example.book_service.controller;

import com.example.book_service.JWT.JWTUtil;
//import com.example.book_service.dto.AIData.UserIsbnResponseDto;
import com.example.book_service.dto.UserUpdateRequestDto;
import com.example.book_service.dto.mybook.UserResponseDto;
import com.example.book_service.entity.UserEntity;
import com.example.book_service.entity.UserRecommendation.UserRecommendation;
//import com.example.book_service.service.AI.UserRecommendationService;
import com.example.book_service.service.BookService;
import com.example.book_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.book_service.entity.UserRecommendation.IsbnNonFilter;
import com.example.book_service.entity.UserRecommendation.IsbnFilter;
//import com.example.book_service.service.AI.UserRecommendationService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private final JWTUtil jwtUtil;

    @Autowired
    private BookService bookService;

//    @Autowired
//    private UserRecommendationService userRecommendationService;

    public UserController(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    //username 중복 확인
    @GetMapping("/user/check-username")
    public ResponseEntity<?> checkUsernameAvailability(@RequestParam(value = "username") String username) {
        boolean isAvailable = userService.isUsernameAvailable(username);
        System.out.println(isAvailable);
        if (isAvailable) {
            return ResponseEntity.ok("Username is available");
        } else {
            return ResponseEntity.badRequest().body("Username is already taken");
        }
    }

    // Nickname 중복 확인
    @GetMapping("/user/check-nickname")
    public ResponseEntity<?> checkNicknameAvailability(@RequestParam(value = "nickname") String nickname) {
        boolean isAvailable = userService.isNicknameAvailable(nickname);
        if (isAvailable) {
            return ResponseEntity.ok("Nickname is available");
        } else {
            return ResponseEntity.badRequest().body("Nickname is already taken");
        }
    }
    @GetMapping("/user/check-password")
    public ResponseEntity<?> checkPasswordAvailability(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password) {
        boolean isAvailable = userService.checkPassword(username, password);
        if (isAvailable) {
            return ResponseEntity.ok("Password is correct");
        } else {
            return ResponseEntity.badRequest().body("Password is wrong");
        }
    }

    @DeleteMapping("/user")
    public ResponseEntity<?> deleteUser(@RequestParam(value = "username") String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok().build(); // 성공적으로 처리되었을 때 HTTP 200 OK 응답 반환
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponseDto> getUserDetails(@RequestHeader(name = "Authorization") String token) {
        String cleanedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        String username = jwtUtil.getUsername(cleanedToken);
        UserEntity user = userService.getUserByUsername(username);
        UserResponseDto userResponseDto = new UserResponseDto(user);
        return ResponseEntity.ok(userResponseDto);
    }

    @PutMapping("/user/update")
    public ResponseEntity<String> updateUser(@RequestHeader(name = "Authorization") String token,
                                             @RequestBody UserUpdateRequestDto userUpdateRequestDto) throws Exception {
        String cleanedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        String username = jwtUtil.getUsername(cleanedToken);

        userService.updateUser(username, userUpdateRequestDto);
        return ResponseEntity.ok("User details updated successfully");
    }

    @GetMapping("/user/isbn_list")
    public Map<String, Object> getIsbnList() throws IOException {
        return bookService.getIsbnList();
    }


    //사용자의 추천 isbn db에서 불러오기
//    @GetMapping("/user/isbn_list")
//    public ResponseEntity<UserIsbnResponseDto> getUserIsbnList(@RequestHeader(name = "Authorization") String token) {
//        String cleanedToken = token.startsWith("Bearer ") ? token.substring(7) : token;
//        String username = jwtUtil.getUsername(cleanedToken);
//        UserEntity user = userService.getUserByUsername(username);
//
//        UserRecommendation userRecommendation = userRecommendationService.getUserRecommendation(user);
//
//        List<Long> isbnNonFilterList = userRecommendation.getIsbnNonFilter().stream()
//                .map(IsbnNonFilter::getIsbn) // IsbnNonFilter 클래스가 isbn을 반환하는 getIsbn 메서드를 가진다고 가정합니다.
//                .collect(Collectors.toList());
//
//        List<Long> isbnFilterList = userRecommendation.getIsbnFilter().stream()
//                .map(IsbnFilter::getIsbn) // IsbnFilter 클래스가 isbn을 반환하는 getIsbn 메서드를 가진다고 가정합니다.
//                .collect(Collectors.toList());
//
//        UserIsbnResponseDto isbnResponseDto = new UserIsbnResponseDto();
//        isbnResponseDto.setUsername(username);
//        isbnResponseDto.setIsbnNonFilter(isbnNonFilterList);
//        isbnResponseDto.setIsbnFilter(isbnFilterList);
//
//
//        return ResponseEntity.ok(isbnResponseDto);
//    }

}
