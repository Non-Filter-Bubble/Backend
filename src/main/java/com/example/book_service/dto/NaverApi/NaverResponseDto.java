package com.example.book_service.dto.NaverApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NaverResponseDto {

    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<Item> items;

    @Override
    public String toString() {
        return "NaverResponseDto{" +
                "lastBuildDate='" + lastBuildDate + '\'' +
                ", total=" + total +
                ", start=" + start +
                ", display=" + display +
                ", items=" + items +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Item {
        private String title;
        private String link;
        private String image;
        private String author;
        private String discount;
        private String publisher;
        private String pubdate;
        private String isbn;
        private String description;

        @Override
        public String toString() {
            return "Item{" +
                    "title='" + title + '\'' +
                    ", link='" + link + '\'' +
                    ", image='" + image + '\'' +
                    ", author='" + author + '\'' +
                    ", discount='" + discount + '\'' +
                    ", publisher='" + publisher + '\'' +
                    ", pubdate='" + pubdate + '\'' +
                    ", isbn='" + isbn + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
