package com.notificationservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "qrcode-service", path = "/v1/api/qrcode")
public interface QRCodeServiceClient {
    @GetMapping("/generate")
    ResponseEntity<Resource> generateQRCode(@RequestParam String qrCodeData);
}
