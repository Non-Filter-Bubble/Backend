package com.example.book_service.searchLibraryAPI.controller;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryDTO {

    @JsonProperty("libCode")
    private String libCode;

    @JsonProperty("libName")
    private String libName;

    @JsonProperty("address")
    private String address;

    @JsonProperty("tel")
    private String tel;

    @JsonProperty("fax")
    private String fax;

    @JsonProperty("latitude")
    private String latitude;

    @JsonProperty("longitude")
    private String longitude;

    @JsonProperty("homepage")
    private String homepage;

    @JsonProperty("closed")
    private String closed;

    @JsonProperty("operatingTime")
    private String operatingTime;
}