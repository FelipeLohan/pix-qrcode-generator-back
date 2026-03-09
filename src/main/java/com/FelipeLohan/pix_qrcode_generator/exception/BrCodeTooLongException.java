package com.FelipeLohan.pix_qrcode_generator.exception;

public class BrCodeTooLongException extends RuntimeException {

    public BrCodeTooLongException() {
        super("BR Code excede o limite de 512 caracteres.");
    }
}
