package com.example.book_service.service;

import com.example.book_service.dto.GenrePreferencesDTO;
import com.example.book_service.entity.GenreEntity;
import com.example.book_service.entity.UserEntity;
import com.example.book_service.repository.GenrePreferenceRepository;
import com.example.book_service.repository.GenreRepository;
import com.example.book_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreService {

    private final GenrePreferenceRepository genrePreferenceRepository;
    private final UserRepository userRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    public GenreService(GenrePreferenceRepository genrePreferenceRepository, UserRepository userRepository) {
        this.genrePreferenceRepository = genrePreferenceRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public void GenreProcess(GenrePreferencesDTO genrePreferencesDTO, String username){
        UserEntity user = userRepository.findByUsername(username);

        if (user == null) {
            throw new IllegalArgumentException("No user found with username: " + username);
        }

        GenreEntity genreEntity = new GenreEntity();
        genreEntity.setUser(user);
        genreEntity.setFavGenre(genrePreferencesDTO.getFavGenre()); // 리스트를 문자열로 변환하여 저장
        genreEntity.setFavBookType(genrePreferencesDTO.getFavBookType()); // 같은 방식으로 책 유형도 처리
        genrePreferenceRepository.save(genreEntity);
    }

    public boolean checkUserGenreExists(int userId) {
        Optional<GenreEntity> genre = genreRepository.findByUserUserid(userId);
        return genre.isPresent();
    }
    public List<String> getFavoriteGenresByUserId(int userid) {
        return genreRepository.findAllByUserUserid(userid).stream()
                .flatMap(genreEntity -> genreEntity.getFavGenreList().stream())
                .collect(Collectors.toList());
    }
}
