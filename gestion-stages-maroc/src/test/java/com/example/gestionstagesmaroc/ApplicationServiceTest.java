package com.example.gestionstagesmaroc.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationServiceTest {

    @Autowired
    private ApplicationService applicationService;

    @Test
    void testGetApplications() {
        assertThat(applicationService.getApplicationsByUserEmail("test@test.com")).isEmpty();
    }

    @Test
    void testPostuler() {
        applicationService.postuler("test@test.com", 999L);
    }
}
