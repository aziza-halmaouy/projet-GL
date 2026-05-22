package com.example.gestionstagesmaroc;

import com.example.gestionstagesmaroc.controller.AuthController;
import com.example.gestionstagesmaroc.model.User;
import com.example.gestionstagesmaroc.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepo;

    @Test
    void testLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testRegisterPage() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
    void testLoginSubmit_success() throws Exception {
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("1234");

        when(userRepo.findByEmail("test@test.com")).thenReturn(user);

        mockMvc.perform(post("/login")
                        .param("email", "test@test.com")
                        .param("password", "1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard"));
    }

    @Test
    void testLoginSubmit_wrongPassword() throws Exception {
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("correct");

        when(userRepo.findByEmail("test@test.com")).thenReturn(user);

        mockMvc.perform(post("/login")
                        .param("email", "test@test.com")
                        .param("password", "wrong"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void testLoginSubmit_userNotFound() throws Exception {
        when(userRepo.findByEmail("nobody@test.com")).thenReturn(null);

        mockMvc.perform(post("/login")
                        .param("email", "nobody@test.com")
                        .param("password", "1234"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void testRegisterSubmit_success() throws Exception {
        when(userRepo.findByEmail("nouveau@test.com")).thenReturn(null);

        mockMvc.perform(post("/register")
                        .param("nom", "Alami")
                        .param("prenom", "Sara")
                        .param("email", "nouveau@test.com")
                        .param("password", "pass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?registered"));

        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterSubmit_emailDejaUtilise() throws Exception {
        User existing = new User();
        existing.setEmail("existant@test.com");

        when(userRepo.findByEmail("existant@test.com")).thenReturn(existing);

        mockMvc.perform(post("/register")
                        .param("nom", "Test")
                        .param("prenom", "User")
                        .param("email", "existant@test.com")
                        .param("password", "pass"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    void testLogout() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout"));
    }
}