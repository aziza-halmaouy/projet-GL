package com.example.gestionstagesmaroc;

import com.example.gestionstagesmaroc.controller.InternshipController;
import com.example.gestionstagesmaroc.model.Internship;
import com.example.gestionstagesmaroc.model.Application;
import com.example.gestionstagesmaroc.service.ApplicationService;
import com.example.gestionstagesmaroc.service.InternshipService;
import com.example.gestionstagesmaroc.service.QRCodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InternshipController.class)
class InternshipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InternshipService internshipService;

    @MockBean
    private ApplicationService applicationService;

    @MockBean
    private QRCodeService qrCodeService;

    // ─── /offres ────────────────────────────────────────────────

    @Test
    void testOffres_sansSession_redirect() throws Exception {
        mockMvc.perform(get("/offres"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void testOffres_avecSession_ok() throws Exception {
        Internship i1 = new Internship();
        i1.setTitle("Stage Dev");
        i1.setCompany("TechMaroc");
        i1.setWebsite("https://techmaroc.ma");

        when(internshipService.getAll()).thenReturn(List.of(i1));
        when(qrCodeService.generateQRCode(anyString())).thenReturn("base64qrcode");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userEmail", "test@test.com");

        mockMvc.perform(get("/offres").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("offres"))
                .andExpect(model().attributeExists("internships"));
    }

    @Test
    void testOffres_listeVide() throws Exception {
        when(internshipService.getAll()).thenReturn(List.of());

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userEmail", "test@test.com");

        mockMvc.perform(get("/offres").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("offres"))
                .andExpect(model().attributeExists("internships"));
    }

    // ─── /postuler/{id} ─────────────────────────────────────────

    @Test
    void testPostuler_sansSession_redirect() throws Exception {
        mockMvc.perform(get("/postuler/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void testPostuler_avecSession_redirect() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userEmail", "test@test.com");

        doNothing().when(applicationService).postuler("test@test.com", 1L);

        mockMvc.perform(get("/postuler/1").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/candidatures"));

        verify(applicationService, times(1)).postuler("test@test.com", 1L);
    }

    // ─── /candidatures ──────────────────────────────────────────

    @Test
    void testCandidatures_sansSession_redirect() throws Exception {
        mockMvc.perform(get("/candidatures"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void testCandidatures_avecSession_ok() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userEmail", "test@test.com");

        Application app = new Application();
        app.setStatus("En attente");

        when(applicationService.getApplicationsByUserEmail("test@test.com"))
                .thenReturn(List.of(app));

        mockMvc.perform(get("/candidatures").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("candidatures"))
                .andExpect(model().attributeExists("applications"));
    }

    @Test
    void testCandidatures_listeVide() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userEmail", "test@test.com");

        when(applicationService.getApplicationsByUserEmail("test@test.com"))
                .thenReturn(List.of());

        mockMvc.perform(get("/candidatures").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("candidatures"))
                .andExpect(model().attributeExists("applications"));
    }
}
