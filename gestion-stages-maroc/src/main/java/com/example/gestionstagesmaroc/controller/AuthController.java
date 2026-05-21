package com.example.gestionstagesmaroc.controller;

import com.example.gestionstagesmaroc.model.User;
import com.example.gestionstagesmaroc.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserRepository userRepo;

    public AuthController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String email,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {
        User user = userRepo.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Email ou mot de passe incorrect");
            return "login";
        }
        session.setAttribute("userEmail", email);
        return "redirect:/dashboard";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@RequestParam String nom,
                                 @RequestParam String prenom,
                                 @RequestParam String email,
                                 @RequestParam String password,
                                 Model model) {
        if (userRepo.findByEmail(email) != null) {
            model.addAttribute("error", "Cet email est déjà utilisé !");
            return "register";
        }
        User user = new User();
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setEmail(email);
        user.setPassword(password);
        userRepo.save(user);
        return "redirect:/login?registered";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null) return "redirect:/login";
        model.addAttribute("userName", email.split("@")[0]);
        return "dashboard";
    }

    @GetMapping("/profil")
    public String profil(HttpSession session, Model model) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null) return "redirect:/login";
        User user = userRepo.findByEmail(email);
        model.addAttribute("user", user);
        return "profil";
    }

    @PostMapping("/profil/edit")
    public String editProfil(@ModelAttribute User updatedUser, HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null) return "redirect:/login";
        User user = userRepo.findByEmail(email);
        if (user != null) {
            user.setNom(updatedUser.getNom());
            user.setPrenom(updatedUser.getPrenom());
            user.setTelephone(updatedUser.getTelephone());
            user.setEcole(updatedUser.getEcole());
            user.setAdresse(updatedUser.getAdresse());
            user.setNiveauScolaire(updatedUser.getNiveauScolaire());
            userRepo.save(user);
        }
        return "redirect:/profil";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}