package com.example.gestionstagesmaroc.repository;

import com.example.gestionstagesmaroc.model.Application;
import com.example.gestionstagesmaroc.model.Internship;
import com.example.gestionstagesmaroc.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;NON_KEYWORDS=VALUE",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
})
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
        User user = new User();
        user.setNom("Alami");
        user.setPrenom("Youssef");
        user.setEmail("youssef@test.com");
        user.setPassword("1234");
        userRepository.save(user);

        Internship internship = new Internship();
        internship.setTitle("Stage Dev");
        internship.setCompany("TechMaroc");
        internship.setLocation("Casablanca");
        internship.setDuration("2 mois");
        internshipRepository.save(internship);

        Application app = new Application();
        app.setUser(user);
        app.setInternship(internship);
        app.setStatus("En attente");
        applicationRepository.save(app);

        List<Application> apps = applicationRepository.findByUser(user);
        assertThat(apps).hasSize(1);
        assertThat(apps.get(0).getStatus()).isEqualTo("En attente");
    }
}