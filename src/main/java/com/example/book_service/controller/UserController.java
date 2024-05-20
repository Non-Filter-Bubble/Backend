package com.example.book_service.controller;

import com.example.book_service.JWT.JWTUtil;
import com.example.book_service.dto.UserUpdateRequestDto;
import com.example.book_service.dto.mybook.UserResponseDto;
import com.example.book_service.entity.UserEntity;
import com.example.book_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    @Autowired
    private UserService userService;
    private final JWTUtil jwtUtil;

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



}
