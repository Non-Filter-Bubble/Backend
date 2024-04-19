package com.example.book_service.service.mybook;


import com.example.book_service.domain.mybook.MybookEntity;
import com.example.book_service.domain.mybook.MybookRepository;
import com.example.book_service.dto.mybook.MybookUpdateRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MybookService {
    public final MybookRepository mybookRepository;
    @Transactional
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
