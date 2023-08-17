package com.notificationservice.service;

import com.notificationservice.dto.MailContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Value("${spring.mail.addresses.from}")
    private String emailFromAddress;

    @Value("${spring.mail.addresses.replyTo}")
    private String emailReplyToAddress;

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendEmail(String recipient, String subject, MailContent content) throws MailException {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(emailFromAddress);
            messageHelper.setReplyTo(emailReplyToAddress);
            messageHelper.setTo(recipient);
            messageHelper.setSubject(subject);
            messageHelper.setText(content.getText(), content.getHtml());
        };
        emailSender.send(messagePreparator);
    }
}
