package com.example.gestionstagesmaroc;

import com.example.gestionstagesmaroc.service.QRCodeService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class QRCodeServiceTest {

    private final QRCodeService qrCodeService = new QRCodeService();

    @Test
    void testGenerateQRCode_validUrl() {
        String result = qrCodeService.generateQRCode("https://example.com");
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
    }

    @Test
    void testGenerateQRCode_nullUrl() {
        // Doit utiliser l'URL par défaut
        String result = qrCodeService.generateQRCode(null);
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
    }

    @Test
    void testGenerateQRCode_blankUrl() {
        // Doit utiliser l'URL par défaut
        String result = qrCodeService.generateQRCode("   ");
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
    }

    @Test
    void testGenerateQRCode_returnsBase64() {
        String result = qrCodeService.generateQRCode("https://google.com");
        // Base64 ne contient pas d'espaces
        assertThat(result).doesNotContain(" ");
    }
}