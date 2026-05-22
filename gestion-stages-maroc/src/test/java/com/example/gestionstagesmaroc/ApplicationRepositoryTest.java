package com.example.gestionstagesmaroc;

import com.example.gestionstagesmaroc.model.Application;
import com.example.gestionstagesmaroc.model.Internship;
import com.example.gestionstagesmaroc.model.User;
import com.example.gestionstagesmaroc.repository.ApplicationRepository;
import com.example.gestionstagesmaroc.repository.InternshipRepository;
import com.example.gestionstagesmaroc.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ApplicationRepositoryTest {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InternshipRepository internshipRepository;

    @Test
    void testFindAll() {
        assertThat(applicationRepository.findAll()).isNotNull();
    }

    @Test
    void testSaveAndFindByUser() {
        // Créer un user
        User user = new User();
        user.setNom("Alami");
        user.setPrenom("Youssef");
        user.setEmail("youssef@test.com");
        user.setPassword("1234");
        userRepository.save(user);

        // Créer un internship
        Internship internship = new Internship();
        internship.setTitle("Stage Dev");
        internship.setCompany("TechMaroc");
        internship.setLocation("Casablanca");
        internship.setDuration("2 mois");
        internshipRepository.save(internship);

        // Créer une application
        Application app = new Application();
        app.setUser(user);
        app.setInternship(internship);
        app.setStatus("En attente");
        applicationRepository.save(app);

        // Vérifier
        List<Application> apps = applicationRepository.findByUser(user);
        assertThat(apps).hasSize(1);
        assertThat(apps.get(0).getStatus()).isEqualTo("En attente");
    }
}