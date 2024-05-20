package com.example.book_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String nickname;
    private String currentPassword; // 현재 비밀번호
    private String newPassword; // 새로운 비밀번호

}