package com.example.book_service.repository;

import com.example.book_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

// 여기서 integer은 id 타입
public interface UserRepository extends JpaRepository<UserEntity, Integer> {


    //로그인 조회(DB)
    UserEntity findByUsername(String username);

    UserEntity findByNickname(String nickname);
    boolean existsByUsername(String username);

    //회원 탈퇴
    @Transactional
    void deleteByUsername(String username);




}