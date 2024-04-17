package com.example.book_service.controller;

import com.example.book_service.dto.JoinDTO;
import com.example.book_service.service.JoinService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class JoinController{
    private final JoinService joinService;

    public JoinController(JoinService joinService){

        this.joinService = joinService;
    }


    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO){

        joinService.joinProcess(joinDTO);
        return "ok";
    }
}