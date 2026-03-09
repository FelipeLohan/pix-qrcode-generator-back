package com.FelipeLohan.pix_qrcode_generator.service;

import org.springframework.stereotype.Service;

import java.text.Normalizer;

@Service
public class TextSanitizerService {

    public String sanitize(String input, int maxLength) {
        if (input == null || input.isBlank()) {
            return "";
        }

        String result = input.toUpperCase();
        result = Normalizer.normalize(result, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}", "");
        result = result.replaceAll("[^A-Z0-9 ]", " ");
        result = result.replaceAll(" +", " ").trim();

        if (result.length() > maxLength) {
            result = result.substring(0, maxLength).trim();
        }

        return result;
    }
}
