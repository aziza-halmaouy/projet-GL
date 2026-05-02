package com.example.gestionstagesmaroc.service;

import com.example.gestionstagesmaroc.model.Internship;
import com.example.gestionstagesmaroc.repository.InternshipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InternshipService {

    private final InternshipRepository internshipRepo;

    public InternshipService(InternshipRepository internshipRepo) {
        this.internshipRepo = internshipRepo;
    }

    public List<Internship> getAll() {
        return internshipRepo.findAll();
    }

    public Internship getById(Long id) {
        return internshipRepo.findById(id).orElse(null);
    }
}