package com.example.book_service.bookinfoAPI;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookInfoEntity {
    @JsonProperty("ISBN_THIRTEEN_NO")
    private long isbnThirteenNo;

    @JsonProperty("GENRE_LV1")
    private String genreLv1;

    @JsonProperty("GENRE_LV2")
    private String genreLv2;

    @JsonProperty("INFO_TEXT_BOLD")
    private String infoText;

    @JsonProperty("BOOK_COVER_URL")
    private String bookCoverUrl;

}
