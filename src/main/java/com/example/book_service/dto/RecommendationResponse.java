package com.example.book_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecommendationResponse {
    private int user_id;
    private List<Long> isbn_nonfilter;
    private List<Long> isbn_filter;
}
