package com.example.gestionstagesmaroc.repository;

import com.example.gestionstagesmaroc.model.Application;
import com.example.gestionstagesmaroc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByUser(User user);
}