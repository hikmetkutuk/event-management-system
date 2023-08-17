package com.notificationservice.controller;

import com.notificationservice.listener.MailListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/notification")
public class DemoController {
    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);
    private final KafkaTemplate<String, Boolean> kakfaProducer;
    private final KafkaProperties kafkaProperties;

    public DemoController(KafkaTemplate<String, Boolean> kakfaProducer, KafkaProperties kafkaProperties) {
        this.kakfaProducer = kakfaProducer;
        this.kafkaProperties = kafkaProperties;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendProjectStatusEmail() {
        logger.info("Sending mailing request: ");
        kakfaProducer.send("ems", true);
    }
}
