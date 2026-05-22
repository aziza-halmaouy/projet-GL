package com.example.gestionstagesmaroc.service;

import com.example.gestionstagesmaroc.model.*;
import com.example.gestionstagesmaroc.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository appRepo;
    private final UserRepository userRepo;
    private final InternshipRepository internshipRepo;

    public ApplicationService(ApplicationRepository appRepo,
                              UserRepository userRepo,
                              InternshipRepository internshipRepo) {
        this.appRepo = appRepo;
        this.userRepo = userRepo;
        this.internshipRepo = internshipRepo;
    }

    public void postuler(String email, Long internshipId) {
        User user = userRepo.findByEmail(email);
        Internship internship = internshipRepo.findById(internshipId).orElse(null);

        if (user != null && internship != null) {
            boolean dejaPostule = appRepo.findByUser(user).stream()
                    .anyMatch(app -> app.getInternship().getId().equals(internshipId));

            if (!dejaPostule) {
                Application app = new Application();
                app.setUser(user);
                app.setInternship(internship);
                app.setStatus("En attente");
                appRepo.save(app);
            }
        }
    }

    public List<Application> getApplicationsByUserEmail(String email) {
        User user = userRepo.findByEmail(email);
        return (user != null) ? appRepo.findByUser(user) : List.of();
    }
}