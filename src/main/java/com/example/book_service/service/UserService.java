package com.example.book_service.service;

import com.example.book_service.entity.UserEntity;
import com.example.book_service.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean checkPassword(String username, String password){
        UserEntity user = userRepository.findByUsername(username);
        if (user==null){
            return false;
        }

        return passwordEncoder.matches(password,user.getPassword());
    }

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

    @Transactional
    public void deleteUser(String username) {
        UserEntity existingUser = userRepository.findByUsername(username);
        if (existingUser == null) {
            throw new UserNotFoundException(username);
        }
        userRepository.deleteByUsername(username);
    }

    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String username) {
            super("No user found with username: " + username);
        }
    }

}
