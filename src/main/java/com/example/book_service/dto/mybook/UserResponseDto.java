package com.example.book_service.dto.mybook;

import com.example.book_service.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    private int userid;
    private String username;
    private String nickname;

    public UserResponseDto(UserEntity user) {
        this.userid = user.getUserid();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
    }

}
