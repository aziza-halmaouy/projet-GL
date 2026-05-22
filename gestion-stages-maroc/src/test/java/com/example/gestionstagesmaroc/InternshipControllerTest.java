package com.example.gestionstagesmaroc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.gestionstagesmaroc.service.InternshipService;
import com.example.gestionstagesmaroc.service.ApplicationService;
import com.example.gestionstagesmaroc.service.QRCodeService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Test
    void testOffresRedirectIfNotLogged() throws Exception {
        mockMvc.perform(get("/offres"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testCandidaturesRedirectIfNotLogged() throws Exception {
        mockMvc.perform(get("/candidatures"))
                .andExpect(status().is3xxRedirection());
    }
}
