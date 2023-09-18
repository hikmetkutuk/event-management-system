package com.notificationservice.service;

import com.notificationservice.dto.MailContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSimpleMailMessage(MailContent request) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setSubject("Test");
            message.setFrom(from);
            message.setTo(request.getEmail());
            message.setText(request.getText());

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
