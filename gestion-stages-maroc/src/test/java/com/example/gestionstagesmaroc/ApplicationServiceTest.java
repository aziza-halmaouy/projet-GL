package com.example.gestionstagesmaroc;

import com.example.gestionstagesmaroc.model.Application;
import com.example.gestionstagesmaroc.model.Internship;
import com.example.gestionstagesmaroc.model.User;
import com.example.gestionstagesmaroc.repository.ApplicationRepository;
import com.example.gestionstagesmaroc.repository.InternshipRepository;
import com.example.gestionstagesmaroc.repository.UserRepository;
import com.example.gestionstagesmaroc.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ApplicationServiceTest {

    @Mock
    private ApplicationRepository appRepo;

    @Mock
    private UserRepository userRepo;

    @Mock
    private InternshipRepository internshipRepo;

    @InjectMocks
    private ApplicationService applicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPostuler_success() {
        User user = new User();
        user.setEmail("test@test.com");

        Internship internship = new Internship();
        internship.setId(1L);

        when(userRepo.findByEmail("test@test.com")).thenReturn(user);
        when(internshipRepo.findById(1L)).thenReturn(Optional.of(internship));
        when(appRepo.findByUser(user)).thenReturn(List.of());

        applicationService.postuler("test@test.com", 1L);

        verify(appRepo, times(1)).save(any(Application.class));
    }

    @Test
    void testPostuler_dejaPostule() {
        User user = new User();
        user.setEmail("test@test.com");

        Internship internship = new Internship();
        internship.setId(1L);

        Application existing = new Application();
        existing.setInternship(internship);

        when(userRepo.findByEmail("test@test.com")).thenReturn(user);
        when(internshipRepo.findById(1L)).thenReturn(Optional.of(internship));
        when(appRepo.findByUser(user)).thenReturn(List.of(existing));

        applicationService.postuler("test@test.com", 1L);

        verify(appRepo, never()).save(any(Application.class));
    }

    @Test
    void testPostuler_userInexistant() {
        when(userRepo.findByEmail("ghost@test.com")).thenReturn(null);

        applicationService.postuler("ghost@test.com", 1L);

        verify(appRepo, never()).save(any());
    }

    @Test
    void testGetApplicationsByUserEmail() {
        User user = new User();
        user.setEmail("test@test.com");

        Application app = new Application();
        app.setUser(user);

        when(userRepo.findByEmail("test@test.com")).thenReturn(user);
        when(appRepo.findByUser(user)).thenReturn(List.of(app));

        List<Application> result = applicationService.getApplicationsByUserEmail("test@test.com");

        assertThat(result).hasSize(1);
    }

    @Test
    void testGetApplicationsByUserEmail_userNull() {
        when(userRepo.findByEmail("nobody@test.com")).thenReturn(null);

        List<Application> result = applicationService.getApplicationsByUserEmail("nobody@test.com");

        assertThat(result).isEmpty();
    }
}