package com.example.gestionstagesmaroc.controller;

import com.example.gestionstagesmaroc.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class InternshipController {

    private final InternshipService internshipService;
    private final ApplicationService applicationService;
    private final QRCodeService qrCodeService;

    public InternshipController(InternshipService internshipService,
                                ApplicationService applicationService,
                                QRCodeService qrCodeService) {
        this.internshipService = internshipService;
        this.applicationService = applicationService;
        this.qrCodeService = qrCodeService;
    }

    @GetMapping("/offres")
    public String offres(HttpSession session, Model model) {

        if (session.getAttribute("userEmail") == null) {
            return "redirect:/login";
        }

        model.addAttribute("internships", internshipService.getAll()); // ✅ FIX
        model.addAttribute("qrService", qrCodeService);

        return "offres";
    }

    @GetMapping("/postuler/{id}")
    public String postuler(@PathVariable Long id, HttpSession session) {

        String email = (String) session.getAttribute("userEmail");

        if (email == null) {
            return "redirect:/login";
        }

        applicationService.postuler(email, id);
        return "redirect:/candidatures";
    }

    @GetMapping("/candidatures")
    public String candidatures(HttpSession session, Model model) {

        String email = (String) session.getAttribute("userEmail");

        if (email == null) {
            return "redirect:/login";
        }

        model.addAttribute("applications",
                applicationService.getApplicationsByUserEmail(email));

        return "candidatures";
    }
}