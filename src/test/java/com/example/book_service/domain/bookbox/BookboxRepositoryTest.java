package com.example.book_service.domain.bookbox;

import com.example.book_service.entity.UserEntity;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BookboxRepositoryTest {
    @Autowired
    BookboxRepository bookboxRepository;

    @After
    public void cleanup() {
        bookboxRepository.deleteAll();
    }

    @Test
    public void 북서랍저장_불러오기() {
        // given

        // when

        // then
    }
}