package com.api_food.Algaworks_Food.infra.service.email;

import com.api_food.Algaworks_Food.domain.exception.custom.EmailException;
import com.api_food.Algaworks_Food.domain.service.EmailSendService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Slf4j
@RequiredArgsConstructor
public class SmtpEmailSendService implements EmailSendService {

    private final Configuration freemarkerConfig;
    private final JavaMailSender mailSender;
    private final String sender;

    @Override
    public void send(EmailMessage message) {
        try {
            MimeMessage mimeMessage = createMimeMessage(message);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("Failed to send email", e);
            throw new EmailException("Error to send email: " + e.getMessage());
        }
    }

    protected MimeMessage createMimeMessage(EmailMessage message) throws MessagingException {
        String body = templateProcess(message);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

        helper.setFrom(sender);
        helper.setTo(message.getRecipients().toArray(new String[0]));
        helper.setSubject(message.getSubject());
        helper.setText(body, true);

        return mimeMessage;
    }

    protected String templateProcess(EmailMessage message) {
        try {
            Template template = freemarkerConfig.getTemplate(message.getBody());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getModels());
        } catch (Exception e) {
            throw new EmailException(e.getMessage());
        }
    }
}
