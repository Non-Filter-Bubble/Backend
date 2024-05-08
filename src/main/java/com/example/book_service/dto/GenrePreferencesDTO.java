package com.example.book_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GenrePreferencesDTO {

    private List<String> favGenre;
    private List<String> favBookType;
}
