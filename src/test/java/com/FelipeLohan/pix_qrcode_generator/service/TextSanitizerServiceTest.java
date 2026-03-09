package com.FelipeLohan.pix_qrcode_generator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextSanitizerServiceTest {

    private TextSanitizerService sanitizer;

    @BeforeEach
    void setUp() {
        sanitizer = new TextSanitizerService();
    }

    @Test
    void sanitizeRemovesAccents() {
        assertEquals("FELIPE GONCALVES", sanitizer.sanitize("Felipe Gonçalves", 25));
    }

    @Test
    void sanitizeConvertsToUpperCase() {
        assertEquals("SAO PAULO", sanitizer.sanitize("sao paulo", 15));
    }

    @Test
    void sanitizeTruncatesToMaxLength() {
        String input = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234"; // 30 chars
        String result = sanitizer.sanitize(input, 25);
        assertEquals(25, result.length());
    }

    @Test
    void sanitizeRemovesSpecialChars() {
        assertEquals("ACAO", sanitizer.sanitize("Ação!", 25));
    }

    @Test
    void sanitizeCollapsesMultipleSpaces() {
        assertEquals("ACAO REACAO", sanitizer.sanitize("Ação & Reação", 25));
    }

    @Test
    void sanitizeReturnsEmptyForNullInput() {
        assertEquals("", sanitizer.sanitize(null, 25));
    }
}
