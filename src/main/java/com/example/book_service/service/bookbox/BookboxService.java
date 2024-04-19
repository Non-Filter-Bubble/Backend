package com.example.book_service.service.bookbox;


import com.example.book_service.domain.bookbox.BookboxEntity;
import com.example.book_service.domain.bookbox.BookboxRepository;
import com.example.book_service.dto.bookbox.BookboxResponseDto;
import com.example.book_service.dto.bookbox.BookboxSaveRequestDto;
import com.example.book_service.entity.UserEntity;
import com.example.book_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookboxService {
    public final BookboxRepository bookboxRepository;
    public final UserRepository userRepository;


    /* save() : 북서랍 저장 메소드 */
    @Transactional
    public Long save(BookboxSaveRequestDto requestDto) {

        // userid 값으로 UserEntity를 조회
        UserEntity user = userRepository.findById(requestDto.getUserid())
                .orElseThrow(() -> new IllegalArgumentException());
        BookboxEntity bookbox = requestDto.toEntity(user);
        return bookboxRepository.save(bookbox).getBookboxid();
    }

    /* findById() :  id값에 해당하는 북 서랍 조회 메소드 */
    public BookboxResponseDto findById(Long id) {
        BookboxEntity entity = bookboxRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 bookbox (" + id + ")가 존재하지 않습니다."));

        // 조회된 entity를 DTO로 변환하여 반환
        return new BookboxResponseDto(entity);
    }
}
