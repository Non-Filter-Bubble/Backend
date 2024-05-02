package com.example.book_service.service;

import com.example.book_service.dto.GenrePreferencesDTO;
import com.example.book_service.entity.GenreEntity;
import com.example.book_service.entity.UserEntity;
import com.example.book_service.repository.GenrePreferenceRepository;
import com.example.book_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenreService {

    private final GenrePreferenceRepository genrePreferenceRepository;
    private final UserRepository userRepository;

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
        genreEntity.setFavGenre(genrePreferencesDTO.getFavGenre());
        genreEntity.setFavBookType(genrePreferencesDTO.getFavBookType());

        genrePreferenceRepository.save(genreEntity);
    }
}
