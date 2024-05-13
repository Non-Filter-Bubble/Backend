package com.example.book_service.bookinfoAPI;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookDetails {
    @JsonProperty("TITLE")
    private String TITLE;
    @JsonProperty("AUTHOR")
    private String AUTHOR;
    @JsonProperty("PUBLISHER")
    private String PUBLISHER;
    @JsonProperty("EA_ISBN")
    private String ISBN;

    @Override
    public String toString() {
        return "BookInfo{" +
                "title='" + TITLE + '\'' +
                ", author='" + AUTHOR + '\'' +
                ", publisher='" + PUBLISHER + '\'' +
                ", isbn='" + ISBN + '\'' +
                '}';
    }
}