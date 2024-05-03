package com.example.book_service.service;

import com.example.book_service.entity.UserEntity;
import com.example.book_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean isUsernameAvailable(String username) {

        // username으로 사용자를 찾는 쿼리
        UserEntity user = userRepository.findByUsername(username);
        // user가 null이면 사용자 이름 사용 가능
        return user == null;
    }

    public boolean isNicknameAvailable(String nickname) {
        UserEntity user = userRepository.findByNickname(nickname);
        return user ==null;
    }

}
