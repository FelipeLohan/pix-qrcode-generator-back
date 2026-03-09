package com.FelipeLohan.pix_qrcode_generator.util;

import com.FelipeLohan.pix_qrcode_generator.exception.InvalidPixKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PixKeyValidatorTest {

    private PixKeyValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PixKeyValidator();
    }

    @Test
    void validateAcceptsCpf() {
        assertDoesNotThrow(() -> validator.validate("12345678901"));
    }

    @Test
    void validateAcceptsCnpj() {
        assertDoesNotThrow(() -> validator.validate("12345678000195"));
    }

    @Test
    void validateAcceptsEmail() {
        assertDoesNotThrow(() -> validator.validate("user@email.com"));
    }

    @Test
    void validateAcceptsPhone() {
        assertDoesNotThrow(() -> validator.validate("+5511999998888"));
    }

    @Test
    void validateAcceptsEvp() {
        assertDoesNotThrow(() -> validator.validate("123e4567-e89b-12d3-a456-426614174000"));
    }

    @Test
    void validateThrowsForInvalidKey() {
        assertThrows(InvalidPixKeyException.class, () -> validator.validate("invalido!!"));
    }

    @Test
    void validateThrowsForNullKey() {
        assertThrows(InvalidPixKeyException.class, () -> validator.validate(null));
    }

    @Test
    void validateThrowsForBlankKey() {
        assertThrows(InvalidPixKeyException.class, () -> validator.validate("   "));
    }
}
