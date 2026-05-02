package com.example.gestionstagesmaroc.service;

import com.example.gestionstagesmaroc.model.Internship;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InternshipServiceTest {

    @Autowired
    private InternshipService service;

    @Test
    void testGetAll() {
        List<Internship> list = service.getAll();
        assertNotNull(list);
    }

    @Test
    void testGetById() {
        Internship i = service.getById(1L);
        assertTrue(i == null || i.getId() != null);
    }
}