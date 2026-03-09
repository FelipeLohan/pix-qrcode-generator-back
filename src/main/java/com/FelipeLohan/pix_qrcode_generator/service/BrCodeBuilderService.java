package com.FelipeLohan.pix_qrcode_generator.service;

import com.FelipeLohan.pix_qrcode_generator.util.Crc16Util;
import org.springframework.stereotype.Service;

@Service
public class BrCodeBuilderService {

    public String build(String chavePix, String nomeRecebedor, String cidade) {
        String campo26 = buildCampo26(chavePix);
        String campo59 = "59" + len(nomeRecebedor) + nomeRecebedor;
        String campo60 = "60" + len(cidade) + cidade;

        String preCrc = "000201"
                + "010211"
                + campo26
                + "52040000"
                + "5303986"
                + "5802BR"
                + campo59
                + campo60
                + "62070503***"
                + "6304";

        return preCrc + Crc16Util.calculate(preCrc);
    }

    private String buildCampo26(String chavePix) {
        String sub00 = "0014BR.GOV.BCB.PIX";
        String sub01 = "01" + len(chavePix) + chavePix;
        String conteudo = sub00 + sub01;
        return "26" + len(conteudo) + conteudo;
    }

    private String len(String value) {
        return String.format("%02d", value.length());
    }
}
