package com.qrcodeservice.controller;

import com.qrcodeservice.service.QRCodeService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/qrcode")
public class QRCodeController {

    private final QRCodeService qrCodeService;

    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @GetMapping("/generate")
    public ResponseEntity<Resource> generateQRCode(@RequestParam String qrCodeData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentDispositionFormData("attachment", "qrcode.png");

        return new ResponseEntity<>(qrCodeService.generateQRCode(qrCodeData), headers, HttpStatus.CREATED);
    }
}
