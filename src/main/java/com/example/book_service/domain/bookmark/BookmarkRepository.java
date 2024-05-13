package com.example.book_service.domain.bookmark;

import com.example.book_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {
    List<BookmarkEntity> findAllByUser_Userid(int userid);

    boolean existsByUserAndIsbn(UserEntity user, Long isbn);

}
