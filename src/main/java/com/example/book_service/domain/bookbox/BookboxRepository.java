package com.example.book_service.domain.bookbox;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookboxRepository extends JpaRepository<BookboxEntity,Long> {

    List<BookboxEntity> findAllByUser_Userid(int userid);


}
