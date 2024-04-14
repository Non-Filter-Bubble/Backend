package com.example.book_service.repository;

import com.example.book_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// 여기서 integer은 id 타입
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Boolean existsByUsername(String username);

}