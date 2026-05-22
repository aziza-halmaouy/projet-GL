package com.example.gestionstagesmaroc.repository;

import com.example.gestionstagesmaroc.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
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
    }

    @Test
    void testFindByEmailNotFound() {
        User found = userRepository.findByEmail("inexistant@test.com");
        assertThat(found).isNull();
    }
}