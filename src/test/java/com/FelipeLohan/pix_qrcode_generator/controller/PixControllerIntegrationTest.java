package com.FelipeLohan.pix_qrcode_generator.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PixControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void gerarRetornaQrCodeBase64ComDadosValidos() throws Exception {
        String body = """
                {
                  "chavePix": "exemplo@email.com",
                  "nomeRecebedor": "Felipe Oliveira",
                  "cidade": "Sao Paulo"
                }
                """;

        mockMvc.perform(post("/api/pix/gerar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.qrCodeBase64").isNotEmpty())
                .andExpect(jsonPath("$.qrCodeBase64").value(org.hamcrest.Matchers.not(
                        org.hamcrest.Matchers.startsWith("data:"))));
    }

    @Test
    void gerarRetorna400ComChavePixInvalida() throws Exception {
        String body = """
                {
                  "chavePix": "chave-invalida!!",
                  "nomeRecebedor": "Felipe",
                  "cidade": "SP"
                }
                """;

        mockMvc.perform(post("/api/pix/gerar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Chave PIX inválida."));
    }

    @Test
    void gerarRetorna400ComCamposVazios() throws Exception {
        String body = """
                {
                  "chavePix": "",
                  "nomeRecebedor": "",
                  "cidade": ""
                }
                """;

        mockMvc.perform(post("/api/pix/gerar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void gerarRetorna400ComBodyAusente() throws Exception {
        mockMvc.perform(post("/api/pix/gerar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
