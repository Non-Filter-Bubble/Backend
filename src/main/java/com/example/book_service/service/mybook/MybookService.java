package com.example.book_service.service.mybook;


import com.example.book_service.domain.book.BookRepository;
import com.example.book_service.domain.bookbox.BookboxEntity;
import com.example.book_service.domain.bookbox.BookboxRepository;
import com.example.book_service.domain.mybook.MybookEntity;
import com.example.book_service.domain.mybook.MybookRepository;
import com.example.book_service.dto.mybook.MybookSaveRequestDto;
import com.example.book_service.dto.mybook.MybookUpdateRequestDto;
import com.example.book_service.domain.book.BookEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MybookService {
    public final BookboxRepository bookboxRepository;
    public final MybookRepository mybookRepository;
    public final BookRepository bookRepository;

    @Transactional
    public Long save(MybookSaveRequestDto requestDto) {
        BookboxEntity bookbox = bookboxRepository.findById(requestDto.getBookboxid())
                .orElseThrow(() -> new IllegalArgumentException());
        BookEntity book = bookRepository.findById(requestDto.getBookid())
                .orElseThrow(() -> new IllegalArgumentException());
        MybookEntity mybook = requestDto.toEntity(bookbox, book);
        return mybookRepository.save(mybook).getMybookid();
    }


    public Long update(Long mybookid, MybookUpdateRequestDto requestDto) {
        MybookEntity mybook = mybookRepository.findById(mybookid)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 mybookid=" + mybookid + "가 존재하지 않습니다."
                ));
        mybook.update(requestDto.getComment(), requestDto.getReview());
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
}
