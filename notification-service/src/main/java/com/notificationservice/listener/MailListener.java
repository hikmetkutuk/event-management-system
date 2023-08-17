package com.notificationservice.listener;

import com.notificationservice.service.EmailService;
import com.notificationservice.service.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;

public class MailListener {
    private static final Logger logger = LoggerFactory.getLogger(MailListener.class);
    private final EmailService emailService;
    private final TemplateService templateService;

    public MailListener(EmailService emailService, TemplateService templateService) {
        this.emailService = emailService;
        this.templateService = templateService;
    }

    @KafkaListener(topicPattern = "ems", autoStartup = "true")
    public void listenToProjectStatusChange() {
        logger.info("Request for project status change received: ");

        try {
            emailService.sendEmail(
                    "",
                    "Test",
                    templateService.generateProjectStatusChangeEmail()
            );
        } catch (MailException e) {
            logger.error("Could not send e-mail", e);
        }
    }
}
