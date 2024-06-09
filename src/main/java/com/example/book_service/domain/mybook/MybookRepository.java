package com.example.book_service.domain.mybook;

import com.example.book_service.domain.bookbox.BookboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MybookRepository  extends JpaRepository<MybookEntity,Long> {
    List<MybookEntity> findAllByBookbox(BookboxEntity bookbox);
    boolean existsByBookboxAndIsbn(BookboxEntity bookbox, Long isbn);
    List<MybookEntity> findByUserid(int userid);

    List<MybookEntity> findByIsbn(Long isbn);
}
