package com.example.book_service.controller;

import com.example.book_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

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


}
