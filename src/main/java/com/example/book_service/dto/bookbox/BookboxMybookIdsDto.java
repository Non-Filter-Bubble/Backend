package com.example.book_service.dto.bookbox;


import lombok.Getter;

import java.util.List;

@Getter
public class BookboxMybookIdsDto {
    private Long bookboxid;
    private List<Long> mybookIds;
}
