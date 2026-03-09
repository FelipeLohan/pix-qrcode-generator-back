package com.FelipeLohan.pix_qrcode_generator.util;

import com.FelipeLohan.pix_qrcode_generator.exception.InvalidPixKeyException;
import org.springframework.stereotype.Component;

@Component
public class PixKeyValidator {

    private static final String CPF_PATTERN   = "\\d{11}";
    private static final String CNPJ_PATTERN  = "\\d{14}";
    private static final String EMAIL_PATTERN = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
    private static final String PHONE_PATTERN = "^\\+55\\d{10,11}$";
    private static final String UUID_PATTERN  =
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    public void validate(String chavePix) {
        if (chavePix == null || chavePix.isBlank()) {
            throw new InvalidPixKeyException();
        }

        if (chavePix.matches(CPF_PATTERN)
                || chavePix.matches(CNPJ_PATTERN)
                || chavePix.matches(EMAIL_PATTERN)
                || chavePix.matches(PHONE_PATTERN)
                || chavePix.matches(UUID_PATTERN)) {
            return;
        }

        throw new InvalidPixKeyException();
    }
}
