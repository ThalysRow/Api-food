package com.api_food.Algaworks_Food.infra.service.email;

import com.api_food.Algaworks_Food.domain.exception.custom.EmailException;
import com.api_food.Algaworks_Food.domain.service.EmailSendService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Slf4j
@RequiredArgsConstructor
public class FakeEmailSendService implements EmailSendService {

    private final Configuration freemarkerConfig;

    @Override
    public void send(EmailMessage message) {
        String body = templateProcess(message);

        log.info("[FAKE EMAIL] To: {}", message.getRecipients());
        log.info("[FAKE EMAIL] Subject: {}", message.getSubject());
        log.info("[FAKE EMAIL] Body:\n{}", body);
    }

    private String templateProcess(EmailMessage message) {
        try {
            Template template = freemarkerConfig.getTemplate(message.getBody());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getModels());
        } catch (Exception e) {
            throw new EmailException(e.getMessage());
        }
    }
}
