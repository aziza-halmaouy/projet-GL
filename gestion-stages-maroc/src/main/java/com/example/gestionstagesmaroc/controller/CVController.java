package com.example.gestionstagesmaroc.controller;

import com.example.gestionstagesmaroc.model.User;
import com.example.gestionstagesmaroc.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class CVController {

    private final UserRepository userRepo;

    public CVController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // Page Mes CV
    @GetMapping("/cv")
    public String cvPage(HttpSession session, Model model) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null) return "redirect:/login";

        User user = userRepo.findByEmail(email);
        model.addAttribute("user", user);
        return "cv"; // Vue Thymeleaf à créer
    }

    // Upload du CV
    @PostMapping("/cv/upload")
    public String uploadCV(@RequestParam("cvFile") MultipartFile cvFile,
                           HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null) return "redirect:/login";

        User user = userRepo.findByEmail(email);
        if (user != null && !cvFile.isEmpty()) {
            try {
                String uploadDir = "uploads/cv/";
                String fileName = user.getId() + "_" + cvFile.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);

                Files.createDirectories(filePath.getParent());
                cvFile.transferTo(filePath.toFile());

                user.setCvPath(fileName); // champ à ajouter dans User
                userRepo.save(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/cv";
    }

    // Télécharger le CV
    @GetMapping("/cv/download/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> downloadCV(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("uploads/cv/").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }
}
