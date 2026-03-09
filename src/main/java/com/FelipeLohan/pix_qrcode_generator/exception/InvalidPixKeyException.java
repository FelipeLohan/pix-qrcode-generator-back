package com.FelipeLohan.pix_qrcode_generator.exception;

public class InvalidPixKeyException extends RuntimeException {

    public InvalidPixKeyException() {
        super("Chave PIX inválida.");
    }
}
