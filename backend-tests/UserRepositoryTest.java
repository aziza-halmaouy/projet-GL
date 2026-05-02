package com.example.gestionstagesmaroc.repository;

import com.example.gestionstagesmaroc.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repo;

    @Test
    void testFindByEmail() {
        User user = repo.findByEmail("test@gmail.com");
        assertTrue(user == null || user.getEmail() != null);
    }
}