package com.example.book_service.searchLibraryAPI.dto;

import com.example.book_service.searchLibraryAPI.controller.LibrariesDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibraryResponseDTO {

    @JsonProperty("pageNo")
    private String pageNo;

    @JsonProperty("pageSize")
    private String pageSize;

    @JsonProperty("numFound")
    private String numFound;

    @JsonProperty("resultNum")
    private String resultNum;

    @JsonProperty("libs")
    private LibrariesDTO libs;
}
