package com.example.book_service.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinDTO {
    private int userid;
    private String username;     //id
    private String nickname;    //닉네임
    private String password;    //비밀번호
}
