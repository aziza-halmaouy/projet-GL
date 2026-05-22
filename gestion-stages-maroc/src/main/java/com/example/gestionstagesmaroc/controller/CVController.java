package com.example.gestionstagesmaroc.controller;

import com.example.gestionstagesmaroc.model.User;
import com.example.gestionstagesmaroc.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        return "cv";
    }

    // Upload CV → stocké en base de données
    @PostMapping("/cv/upload")
    public String uploadCV(@RequestParam("cvFile") MultipartFile cvFile,
                           HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null) return "redirect:/login";

        User user = userRepo.findByEmail(email);
        if (user != null && !cvFile.isEmpty()) {
            try {
                user.setCvData(cvFile.getBytes());
                user.setCvFileName(cvFile.getOriginalFilename());
                userRepo.save(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/cv";
    }

    // Télécharger le CV depuis la base de données
    @GetMapping("/cv/download")
    @ResponseBody
    public ResponseEntity<byte[]> downloadCV(HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null) return ResponseEntity.status(401).build();

        User user = userRepo.findByEmail(email);
        if (user != null && user.getCvData() != null) {

            // Déterminer le type de fichier
            String fileName = user.getCvFileName() != null ? user.getCvFileName() : "cv";
            MediaType mediaType = MediaType.APPLICATION_PDF;
            if (fileName.endsWith(".doc") || fileName.endsWith(".docx")) {
                mediaType = MediaType.APPLICATION_OCTET_STREAM;
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + fileName + "\"")
                    .contentType(mediaType)
                    .body(user.getCvData());
        }
        return ResponseEntity.notFound().build();
    }
}