package com.example.book_service.service.mybook;


import com.example.book_service.bookinfoAPI.ApiResponse;
import com.example.book_service.bookinfoAPI.BookClient;
import com.example.book_service.bookinfoAPI.BookDetails;
import com.example.book_service.domain.bookbox.BookboxEntity;
import com.example.book_service.domain.bookbox.BookboxRepository;
import com.example.book_service.domain.mybook.MybookEntity;
import com.example.book_service.domain.mybook.MybookRepository;
import com.example.book_service.dto.mybook.MybookSaveRequestDto;
import com.example.book_service.dto.mybook.MybookUpdateRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MybookService {
    public final BookboxRepository bookboxRepository;
    public final MybookRepository mybookRepository;


    @Transactional
    public Long save(MybookSaveRequestDto requestDto) {

        // bookboxid 값으로 BookboxEntity 조회
        BookboxEntity bookbox = bookboxRepository.findById(requestDto.getBookboxid())
                .orElseThrow(() -> new IllegalArgumentException("Bookbox with id " + requestDto.getBookboxid() + " not found"));

        // ISBN 중복 체크
        if (mybookRepository.existsByBookboxAndIsbn(bookbox, requestDto.getIsbn())) {
            throw new IllegalArgumentException("ISBN already exists in this bookbox. 이미 추가한 책입니다.");
        }

        MybookEntity mybook = requestDto.toEntity(bookbox);

        System.out.println("Mybook created successfully");
        return mybookRepository.save(mybook).getMybookid();

    }


    @Transactional
    public Long update(Long mybookid, MybookUpdateRequestDto requestDto) {
        MybookEntity mybook = mybookRepository.findById(mybookid)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 mybookid=" + mybookid + "가 존재하지 않습니다."
                ));
        mybook.update(requestDto.getComment(), requestDto.getReview());
        mybookRepository.save(mybook);
        return mybookid;
    }

    public String delete(Long mybookid) {
        MybookEntity mybook = mybookRepository.findById(mybookid)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 mybookid=" + mybookid + "가 존재하지 않습니다."
                ));
        mybookRepository.delete(mybook);
        return "delete complete.";
    }


    public List<MybookEntity> getBooksByUserid(int userId) {
        return mybookRepository.findByUserid(userId);
    }

    public List<String> getCommentsByIsbn(Long isbn) {
        List<MybookEntity> mybookEntities = mybookRepository.findByIsbn(isbn);
        if (mybookEntities.isEmpty()) {
            return Collections.singletonList("No comment found for the given ISBN");
        } else {
            return mybookEntities.stream()
                    .map(MybookEntity::getComment)
                    .collect(Collectors.toList());
        }
        }
}
