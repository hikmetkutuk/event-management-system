package com.notificationservice.controller;

import com.notificationservice.dto.MailContent;
import com.notificationservice.service.MailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/mail")
public class MailController {
    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/simple-mail")
    public ResponseEntity<Void> sendSimpleMailMessage(@RequestBody MailContent request) {
        mailService.sendSimpleMailMessage(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
