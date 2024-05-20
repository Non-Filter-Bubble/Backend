package com.example.book_service.service;

import com.example.book_service.dto.UserUpdateRequestDto;
import com.example.book_service.entity.UserEntity;
import com.example.book_service.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



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

    public int getUserIdByUsername(String username) throws Exception {
        UserEntity user = userRepository.findByUsername(username);
        if (user != null) {
            return user.getUserid();
        } else {
            throw new Exception("User not found with username: " + username);
        }
    }

    public UserEntity getUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
    }


    public void updateUser(String username, UserUpdateRequestDto userUpdateRequestDto) {
        // 사용자 엔티티 가져오기
        UserEntity user = getUserByUsername(username);

        // 업데이트할 필드 설정
        if (userUpdateRequestDto.getNickname() != null) {
            user.setNickname(userUpdateRequestDto.getNickname());
        }

        // 새로운 비밀번호를 설정하고 기존의 비밀번호와 일치하는지 확인
        if (userUpdateRequestDto.getNewPassword() != null) {
            String currentPassword = userUpdateRequestDto.getCurrentPassword();
            String newPassword = userUpdateRequestDto.getNewPassword();

            if (passwordEncoder.matches(currentPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));
            } else {
                throw new IllegalArgumentException("Current password does not match.");
            }
        }

        // 변경 내용 저장소에 반영
        userRepository.save(user);
    }

}
