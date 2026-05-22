package com.example.gestionstagesmaroc.repository;

import com.example.gestionstagesmaroc.model.Internship;
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
class InternshipRepositoryTest {

    @Autowired
    private InternshipRepository internshipRepository;

    @Test
    void testFindAll() {
        assertThat(internshipRepository.findAll()).isNotNull();
    }

    @Test
    void testSaveInternship() {
        Internship internship = new Internship();
        internship.setTitle("Stage Data");
        internship.setCompany("DataCorp");
        internship.setLocation("Rabat");
        internship.setDuration("3 mois");
        internship.setDescription("Stage en data science");
        internshipRepository.save(internship);

        List<Internship> all = internshipRepository.findAll();
        assertThat(all).hasSize(1);
        assertThat(all.get(0).getTitle()).isEqualTo("Stage Data");
    }

    @Test
    void testFindById() {
        Internship internship = new Internship();
        internship.setTitle("Stage Web");
        internship.setCompany("WebAgency");
        internshipRepository.save(internship);

        Internship found = internshipRepository.findById(internship.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getCompany()).isEqualTo("WebAgency");
    }
}