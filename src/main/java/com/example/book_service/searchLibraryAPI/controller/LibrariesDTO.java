package com.example.book_service.searchLibraryAPI.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LibrariesDTO {

    @JsonProperty("lib")
    private List<LibraryDTO> lib;
}