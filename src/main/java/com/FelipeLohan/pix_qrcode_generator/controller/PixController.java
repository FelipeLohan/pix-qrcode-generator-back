package com.FelipeLohan.pix_qrcode_generator.controller;

import com.FelipeLohan.pix_qrcode_generator.dto.PixRequest;
import com.FelipeLohan.pix_qrcode_generator.dto.PixResponse;
import com.FelipeLohan.pix_qrcode_generator.service.PixService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pix")
public class PixController {

    private final PixService pixService;

    public PixController(PixService pixService) {
        this.pixService = pixService;
    }

    @PostMapping("/gerar")
    public ResponseEntity<PixResponse> gerar(@Valid @RequestBody PixRequest request) {
        PixResponse response = pixService.gerar(request);
        return ResponseEntity.ok(response);
    }
}
