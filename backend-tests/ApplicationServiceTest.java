package com.example.gestionstagesmaroc.service;

import com.example.gestionstagesmaroc.model.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplicationServiceTest {

    @Autowired
    private ApplicationService service;

    @Test
    void testPostuler() {
        service.postuler("test@gmail.com", 1L);
        assertTrue(true);
    }

    @Test
    void testGetApplications() {
        List<Application> list =
                service.getApplicationsByUserEmail("test@gmail.com");

        assertNotNull(list);
    }
}