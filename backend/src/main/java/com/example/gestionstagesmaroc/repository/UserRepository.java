package com.example.gestionstagesmaroc.repository;

import com.example.gestionstagesmaroc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}