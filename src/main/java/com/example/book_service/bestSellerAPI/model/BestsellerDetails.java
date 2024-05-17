package com.example.book_service.bestSellerAPI.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BestsellerDetails {
    // isbn, 제목, 저자, 출판사, 표지

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("title")
    private String title;

    @JsonProperty("author")
    private String author;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("cover")
    private String cover;



}
