package com.example.book_service.service;

import com.example.book_service.dto.JoinDTO;
import com.example.book_service.dto.bookbox.BookboxSaveRequestDto;
import com.example.book_service.entity.UserEntity;
import com.example.book_service.repository.UserRepository;
import com.example.book_service.service.bookbox.BookboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final BookboxService bookboxService;

    @Autowired
    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, BookboxService bookboxService){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.bookboxService = bookboxService;
    }

    public void joinProcess(JoinDTO joinDTO){
        String nickname = joinDTO.getNickname();
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();

        //회원 존재하는지 확인
        Boolean isExist = userRepository.existsByUsername(username);

        if(isExist){
            return;
        }

        UserEntity data = new UserEntity();
        data.setNickname(nickname);
        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("USER");

        UserEntity savedUser = userRepository.save(data);

        /* 북서랍 생성 */
        int userid = savedUser.getUserid();
        final String[] Genre = {"romance", "SF"};   // 책 장르 저장 배열

        // 장르별 북서랍 생성
        for (String genre : Genre) {
            BookboxSaveRequestDto bookboxSaveRequestDto = new BookboxSaveRequestDto(userid, genre);
            bookboxService.save(bookboxSaveRequestDto);
        }



    }
}
