//package com.example.book_service.bookinfoAPI;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.awt.print.Book;
//import java.io.IOException;
//import java.util.List;
//
//@Service
//public class BookService {
//    private final BookLoader bookLoader;
//    private final BookClient bookClient;
//
//    @Autowired
//    public BookService(BookLoader bookLoader, BookClient bookClient) {
//        this.bookLoader = bookLoader;
//        this.bookClient = bookClient;
//    }
//
//    public Flux<BookInfoEntity> getBooksAndDetails() throws IOException {
//        List<BookInfoEntity> books = bookLoader.loadBooks("share_best_books_data.json");
//        return Flux.fromIterable(books)
//                .flatMap(book -> bookClient.getBookByIsbn(String.valueOf(book.getIsbnThirteenNo()))
//                        .map(details -> {
//                            book.setAdditionalDetails(details);
//                            return book;
//                        }));
//    }
//
//}
//
