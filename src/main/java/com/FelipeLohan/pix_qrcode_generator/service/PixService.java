package com.FelipeLohan.pix_qrcode_generator.service;

import com.FelipeLohan.pix_qrcode_generator.dto.PixRequest;
import com.FelipeLohan.pix_qrcode_generator.dto.PixResponse;
import com.FelipeLohan.pix_qrcode_generator.exception.BrCodeTooLongException;
import com.FelipeLohan.pix_qrcode_generator.util.PixKeyValidator;
import org.springframework.stereotype.Service;

@Service
public class PixService {

    private final TextSanitizerService textSanitizerService;
    private final BrCodeBuilderService brCodeBuilderService;
    private final QrCodeImageService qrCodeImageService;
    private final PixKeyValidator pixKeyValidator;

    public PixService(TextSanitizerService textSanitizerService,
                      BrCodeBuilderService brCodeBuilderService,
                      QrCodeImageService qrCodeImageService,
                      PixKeyValidator pixKeyValidator) {
        this.textSanitizerService = textSanitizerService;
        this.brCodeBuilderService = brCodeBuilderService;
        this.qrCodeImageService = qrCodeImageService;
        this.pixKeyValidator = pixKeyValidator;
    }

    public PixResponse gerar(PixRequest request) {
        String chavePix = "TELEFONE".equals(request.getTipoChave())
                ? "+55" + request.getChavePix()
                : request.getChavePix();

        pixKeyValidator.validate(chavePix);

        String nome = textSanitizerService.sanitize(request.getNomeRecebedor(), 25);
        String cidade = textSanitizerService.sanitize(request.getCidade(), 15);

        String brCode = brCodeBuilderService.build(chavePix, nome, cidade);

        if (brCode.length() > 512) {
            throw new BrCodeTooLongException();
        }

        String base64 = qrCodeImageService.generateBase64(brCode);
        return new PixResponse(base64);
    }
}
