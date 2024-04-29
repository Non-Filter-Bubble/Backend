package com.example.book_service.controller;

import com.example.book_service.dto.JoinDTO;
import com.example.book_service.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class JoinController{
    private final JoinService joinService;

    public JoinController(JoinService joinService){

        this.joinService = joinService;
    }

    @GetMapping("/join")
    public String joinPage(){
        return "join";
    }


    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO){

        joinService.joinProcess(joinDTO);
        return "ok";
    }
}