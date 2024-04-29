package com.example.book_service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@ResponseBody
public class LoginController {

    @PostMapping("/login")
    public String loginPage(){

        return "loginPage";
    }
}
