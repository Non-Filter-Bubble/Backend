package com.example.book_service.repository;

import com.example.book_service.entity.GenreEntity;
import org.hibernate.id.IncrementGenerator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    Optional<GenreEntity> findByUserUserid(int userid); // UserEntity의 userid 필드 참조
    List<GenreEntity> findAllByUserUserid(int userid);
}