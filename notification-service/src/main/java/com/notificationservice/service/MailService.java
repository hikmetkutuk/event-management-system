package com.notificationservice.service;

import com.notificationservice.client.QRCodeServiceClient;
import com.notificationservice.dto.MailContent;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

@Service
public class MailService {
    private static final String UTF_8_ENCODING = "UTF-8";
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender mailSender;
    private final QRCodeServiceClient qrCodeServiceClient;

    public MailService(JavaMailSender mailSender, QRCodeServiceClient qrCodeServiceClient) {
        this.mailSender = mailSender;
        this.qrCodeServiceClient = qrCodeServiceClient;
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

    public void sendSimpleMailMessageWithAttachments(MailContent request) {
        try {
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(request.getSubject());
            helper.setFrom(from);
            helper.setTo(request.getEmail());
            helper.setText(request.getText());

            // generate qrcode
            ResponseEntity<Resource> qrCodeResponse = qrCodeServiceClient.generateQRCode(request.getEmail());
            Resource qrCodeResource = qrCodeResponse.getBody();

            if (qrCodeResource != null) {
                String qrCodeFileName = "qrcode.png";
                byte[] qrCodeBytes = StreamUtils.copyToByteArray(qrCodeResource.getInputStream());
                ByteArrayResource qrCodeByteArrayResource = new ByteArrayResource(qrCodeBytes);

                helper.addAttachment(qrCodeFileName, qrCodeByteArrayResource);
            }

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private MimeMessage getMimeMessage() {
        return mailSender.createMimeMessage();
    }
}
