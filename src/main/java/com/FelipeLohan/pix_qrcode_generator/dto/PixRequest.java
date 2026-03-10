package com.FelipeLohan.pix_qrcode_generator.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PixRequest {

    @NotBlank
    private String chavePix;

    private String tipoChave;

    @NotBlank
    private String nomeRecebedor;

    @NotBlank
    private String cidade;
}
