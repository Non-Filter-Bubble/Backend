package com.example.book_service.service.bookbox;


import com.example.book_service.domain.bookbox.BookboxEntity;
import com.example.book_service.domain.bookbox.BookboxRepository;
import com.example.book_service.domain.mybook.MybookRepository;
import com.example.book_service.dto.bookbox.BookboxResponseDto;
import com.example.book_service.dto.bookbox.BookboxSaveRequestDto;
import com.example.book_service.domain.mybook.MybookEntity;
import com.example.book_service.entity.UserEntity;
import com.example.book_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookboxService {
    public final BookboxRepository bookboxRepository;
    public final UserRepository userRepository;
    public final MybookRepository mybookRepository;


    /* save() : 북서랍 저장 메소드 */
    @Transactional
    public Long save(BookboxSaveRequestDto requestDto) {

        // userid 값으로 UserEntity를 조회
        UserEntity user = userRepository.findById(requestDto.getUserid())
                .orElseThrow(() -> new IllegalArgumentException());
        BookboxEntity bookbox = requestDto.toEntity(user);
        return bookboxRepository.save(bookbox).getBookboxid();
    }

    /* findById() :  bookboxid값에 해당하는 북 서랍 조회 메소드 */
    public BookboxResponseDto findById(Long id) {
        BookboxEntity entity = bookboxRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMessage = "해당 bookboxid=" + id + "가 존재하지 않습니다.";
                    Logger logger = LoggerFactory.getLogger(BookboxService.class);
                    logger.error(errorMessage);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
                });

        // 조회된 entity를 DTO로 변환하여 반환
        return new BookboxResponseDto(entity);
    }

    /* findAllByUserId() :  userid값에 해당하는 북 서랍 목록 조회 메소드 */
    public List<BookboxResponseDto> findAllByUserId(int id) {
        List<BookboxEntity> entities = bookboxRepository.findAllByUser_Userid(id);
        if (entities.isEmpty()) {
            String errorMessage = "해당 userid=" + id + "가 존재하지 않습니다.";
            Logger logger = LoggerFactory.getLogger(BookboxService.class);
            logger.error(errorMessage);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }
        return entities.stream()
                .map(BookboxResponseDto::new)
                .collect(Collectors.toList());
    }

    /* findMybookIdsByUserId() : userid값에 해당하는 뷱서랍에 속한 모든 책을 장르별로 그룹화하여 조회하는 메소드*/
    public Map<String, List<Long>> findMybooksByUserIdAndGroupByGenre(int userId) {
        // 북서랍 목록 조회
        List<BookboxEntity> bookboxes = bookboxRepository.findAllByUser_Userid(userId);

        // 해당 userId의 북서랍이 존재하지 않는 경우
        if (bookboxes.isEmpty()) {
            String errorMessage = "해당 userId=" + userId + "의 북서랍이 존재하지 않습니다.";
            Logger logger = LoggerFactory.getLogger(BookboxService.class);
            logger.error(errorMessage);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }

        Map<String, List<Long>> mybookIdsByGenre = new HashMap<>();

        // 각 북서랍에 대해 책 목록을 조회하여 장르별로 그룹화
        for (BookboxEntity bookbox : bookboxes) {
            List<MybookEntity> mybooks = mybookRepository.findAllByBookbox(bookbox);
            for (MybookEntity mybook : mybooks) {
                String genre = bookbox.getGenre();
                List<Long> mybookIds = mybookIdsByGenre.getOrDefault(genre, new ArrayList<>());
                mybookIds.add(mybook.getMybookid());
                mybookIdsByGenre.put(genre, mybookIds);
            }
        }

        return mybookIdsByGenre;
    }

//    @Service
//    public

}
