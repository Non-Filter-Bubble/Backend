package com.example.book_service.service.bookmark;


import com.example.book_service.domain.bookmark.BookmarkEntity;
import com.example.book_service.domain.bookmark.BookmarkRepository;
import com.example.book_service.dto.bookmark.BookmarkResponseDto;
import com.example.book_service.dto.bookmark.BookmarkSaveRequestDto;
import com.example.book_service.entity.UserEntity;
import com.example.book_service.repository.UserRepository;
import com.example.book_service.service.UserService;
import com.example.book_service.service.bookbox.BookboxService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookmarkService {
    public final BookmarkRepository bookmarkRepository;
    public final UserRepository userRepository;
    public final UserService userService;
    @Transactional
    public ResponseEntity<String> save(BookmarkSaveRequestDto requestDto) throws Exception {
        // 사용자 ID 가져오기
        int userid = requestDto.getUserId();

        UserEntity user = userRepository.findById(userid)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userid));


        // 해당 사용자가 이미 해당 ISBN을 찜했는지 확인
        boolean isDuplicate = bookmarkRepository.existsByUserAndIsbn(user, requestDto.getIsbn());
        if (isDuplicate) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("ISBN already exists in this bookbox. 이미 찜한 책입니다.");
        }

        // 요청 DTO를 엔티티로 변환하여 저장
        BookmarkEntity bookmark = requestDto.toEntity(user);
        Long bookmarkId = bookmarkRepository.save(bookmark).getBookmarkid();
        return ResponseEntity.ok("Bookmark saved successfully with ID: " + bookmarkId);
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

    public boolean deleteBookmark(Long bookmarkid) {
        Optional<BookmarkEntity> bookmarkOptional = bookmarkRepository.findById(bookmarkid);

        if (bookmarkOptional.isPresent()) {
            BookmarkEntity bookmark = bookmarkOptional.get();
            bookmarkRepository.delete(bookmark);
            return true; // 삭제 성공
        } else {
            return false; // 해당 ID에 해당하는 북마크가 없음
        }
    }

}
