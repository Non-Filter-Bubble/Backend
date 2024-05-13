package com.example.book_service.service.bookmark;


import com.example.book_service.domain.bookmark.BookmarkEntity;
import com.example.book_service.domain.bookmark.BookmarkRepository;
import com.example.book_service.dto.bookbox.BookboxResponseDto;
import com.example.book_service.dto.bookmark.BookmarkResponseDto;
import com.example.book_service.dto.bookmark.BookmarkSaveRequestDto;
import com.example.book_service.entity.UserEntity;
import com.example.book_service.repository.UserRepository;
import com.example.book_service.service.bookbox.BookboxService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookmarkService {
    public final BookmarkRepository bookmarkRepository;
    public final UserRepository userRepository;
    @Transactional
    public Long save(BookmarkSaveRequestDto requestDto) {
//        if (requestDto.getMain_screen_selected() == null || requestDto.getSearch_screen_selected() == null ||
//                requestDto.getMain_screen_selected() == null || requestDto.getSearch_screen_selected() == null) {
//            throw new IllegalArgumentException("One or more required fields are null.");
//        }
        // userid 값으로 UserEntity를 조회
        UserEntity user = userRepository.findById(requestDto.getUserid())
                .orElseThrow(() -> new IllegalArgumentException());


        boolean isDuplicate = bookmarkRepository.existsByUserAndIsbn(user, requestDto.getIsbn());
        if (isDuplicate) {
            throw new IllegalArgumentException("ISBN already exists in this bookbox. 이미 찜한 책입니다.");
        }

        BookmarkEntity bookmark = requestDto.toEntity(user);
        return bookmarkRepository.save(bookmark).getBookmarkid();

    }

    public List<BookmarkResponseDto> findAllByUserId(int id) {
        List<BookmarkEntity> entities = bookmarkRepository.findAllByUser_Userid(id);

        if (entities.isEmpty()) {
            String errorMessage = "해당 userid=" + id + "가 존재하지 않습니다.";
            Logger logger = LoggerFactory.getLogger(BookboxService.class);
            logger.error(errorMessage);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }

        return entities.stream()
                .map(BookmarkResponseDto::new)
                .collect(Collectors.toList());
    }
}
