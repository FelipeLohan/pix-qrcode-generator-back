package com.FelipeLohan.pix_qrcode_generator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrCodeBuilderServiceTest {

    private BrCodeBuilderService builder;

    @BeforeEach
    void setUp() {
        builder = new BrCodeBuilderService();
    }

    @Test
    void buildGeneratesCorrectBrCodeForDocumentationExample() {
        String result = builder.build("exemplo@email.com", "FELIPE OLIVEIRA", "SAO PAULO");

        // CRC real calculado pelo algoritmo CRC16-CCITT (o "1D3D" no doc é ilustrativo)
        assertEquals(
            "00020101021126390014BR.GOV.BCB.PIX0117exemplo@email.com" +
            "5204000053039865802BR5915FELIPE OLIVEIRA6009SAO PAULO62070503***6304498F",
            result
        );
    }

    @Test
    void buildDoesNotIncludeField54() {
        String result = builder.build("exemplo@email.com", "FELIPE OLIVEIRA", "SAO PAULO");

        assertFalse(result.contains("5404"));
        assertFalse(result.contains("5403"));
        assertFalse(result.contains("5402"));
    }

    @Test
    void buildResultIsAtMost512Chars() {
        String result = builder.build("exemplo@email.com", "FELIPE OLIVEIRA", "SAO PAULO");
        assertTrue(result.length() <= 512);
    }

    @Test
    void buildContainsAllMandatoryFixedFields() {
        String result = builder.build("exemplo@email.com", "FELIPE OLIVEIRA", "SAO PAULO");

        assertTrue(result.startsWith("000201"));
        assertTrue(result.contains("010211"));
        assertTrue(result.contains("0014BR.GOV.BCB.PIX"));
        assertTrue(result.contains("52040000"));
        assertTrue(result.contains("5303986"));
        assertTrue(result.contains("5802BR"));
        assertTrue(result.contains("62070503***"));
        assertTrue(result.contains("6304"));
    }
}
