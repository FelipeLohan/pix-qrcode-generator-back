package com.FelipeLohan.pix_qrcode_generator.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Crc16UtilTest {

    // Valor de verificação padrão CRC-16/CCITT-FALSE: "123456789" → 0x29B1
    @Test
    void calculateReturnsCorrectCheckValue() {
        assertEquals("29B1", Crc16Util.calculate("123456789"));
    }

    // CRC real do exemplo da documentação (o "1D3D" no doc é ilustrativo)
    @Test
    void calculateReturnsCorrectCrcForDocumentationExample() {
        String preCrc = "00020101021126390014BR.GOV.BCB.PIX0117exemplo@email.com"
                + "5204000053039865802BR5915FELIPE OLIVEIRA6009SAO PAULO62070503***6304";

        assertEquals("498F", Crc16Util.calculate(preCrc));
    }

    @Test
    void calculateReturnsFourCharUpperHex() {
        String result = Crc16Util.calculate("qualquer string");

        assertEquals(4, result.length());
        assertEquals(result.toUpperCase(), result);
    }

    @Test
    void calculateReturnsFourCharsEvenForSmallCrc() {
        // Garante zero-padding quando necessário
        String result = Crc16Util.calculate("");
        assertEquals(4, result.length());
    }
}
