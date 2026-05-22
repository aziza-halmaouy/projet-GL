package com.example.gestionstagesmaroc.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InternshipServiceTest {

    @Autowired
    private InternshipService internshipService;

    @Test
    void testGetAll() {
        assertThat(internshipService.getAll()).isNotNull();
    }

    @Test
    void testGetById() {
        assertThat(internshipService.getById(999L)).isNull();
    }
}
