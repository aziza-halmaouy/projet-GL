package com.example.gestionstagesmaroc.repository;

import com.example.gestionstagesmaroc.model.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplicationRepositoryTest {

    @Autowired
    private ApplicationRepository repo;

    @Test
    void testFindAll() {
        List<Application> list = repo.findAll();
        assertNotNull(list);
    }
}