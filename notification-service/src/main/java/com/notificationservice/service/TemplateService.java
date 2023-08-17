package com.notificationservice.service;

import com.notificationservice.dto.MailContent;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
public class TemplateService {
    private final SpringTemplateEngine templateEngine;

    public TemplateService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public MailContent generateProjectStatusChangeEmail() {
        Context context = new Context();
        return new MailContent(
                templateEngine.process("project-status-change.txt", context),
                templateEngine.process("project-status-change.html", context)
        );
    }
}
