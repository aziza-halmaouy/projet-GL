package com.example.gestionstagesmaroc.repository;

import com.example.gestionstagesmaroc.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        User user = new User();
        user.setNom("Bennani");
        user.setPrenom("Sara");
        user.setEmail("sara@test.com");
        user.setPassword("pass123");

        userRepository.save(user);

        assertThat(userRepository.findByEmail("sara@test.com")).isNotNull();
    }

    @Test
    void testFindByEmail() {
        User user = new User();
        user.setNom("Idrissi");
        user.setPrenom("Karim");
        user.setEmail("karim@test.com");
        user.setPassword("abc");
        userRepository.save(user);

        User found = userRepository.findByEmail("karim@test.com");
        assertThat(found).isNotNull();
        assertThat(found.getNom()).isEqualTo("Idrissi");
        assertThat(found.getPrenom()).isEqualTo("Karim");
    }

    @Test
    void testFindByEmailNotFound() {
        User found = userRepository.findByEmail("inexistant@test.com");
        assertThat(found).isNull();
    }
}