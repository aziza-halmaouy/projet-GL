package com.example.gestionstagesmaroc;

import com.example.gestionstagesmaroc.model.Internship;
import com.example.gestionstagesmaroc.repository.InternshipRepository;
import com.example.gestionstagesmaroc.service.InternshipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class InternshipServiceTest {

    @Mock
    private InternshipRepository internshipRepo;

    @InjectMocks
    private InternshipService internshipService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        Internship i1 = new Internship();
        i1.setTitle("Stage Dev");

        Internship i2 = new Internship();
        i2.setTitle("Stage Data");

        when(internshipRepo.findAll()).thenReturn(List.of(i1, i2));

        List<Internship> result = internshipService.getAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("Stage Dev");
    }

    @Test
    void testGetById_found() {
        Internship internship = new Internship();
        internship.setId(1L);
        internship.setTitle("Stage Web");

        when(internshipRepo.findById(1L)).thenReturn(Optional.of(internship));

        Internship result = internshipService.getById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Stage Web");
    }

    @Test
    void testGetById_notFound() {
        when(internshipRepo.findById(99L)).thenReturn(Optional.empty());

        Internship result = internshipService.getById(99L);

        assertThat(result).isNull();
    }
}