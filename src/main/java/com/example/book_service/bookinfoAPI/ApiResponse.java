package com.example.book_service.bookinfoAPI;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiResponse{
    @JsonProperty("TOTAL_COUNT")
    private String totalCount;
    private List<BookDetails> docs;
    @JsonProperty("PAGE_NO")
    private String pageNo;

    @Override
    public String toString() {
        return "ApiResponse{" +
                "totalCount='" + totalCount + '\'' +
                ", docs=" + docs +
                ", pageNo='" + pageNo + '\'' +
                '}';
    }
}

