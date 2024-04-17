package com.example.book_service.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinDTO {

    private String loginid;     //id
    private String username;    //닉네임
    private String password;    //비밀번호
}
