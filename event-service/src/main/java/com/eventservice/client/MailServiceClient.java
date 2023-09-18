package com.eventservice.client;

import com.eventservice.dto.MailContent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", path = "/v1/api/mail")
public interface MailServiceClient {
    @PostMapping("/simple-mail")
    ResponseEntity<Void> sendSimpleMailMessage(@RequestBody MailContent request);
}
