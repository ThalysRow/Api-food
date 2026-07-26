package com.api_food.Algaworks_Food.infra.service.email;

import com.api_food.Algaworks_Food.domain.exception.custom.EmailException;
import com.api_food.Algaworks_Food.domain.service.EmailSendService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Service
@RequiredArgsConstructor
public class SmtpEmailSendService implements EmailSendService {

    private final Configuration freemarkerConfig;
    private final JavaMailSender mailSender;

    @Value("${MAIL_SENDER}")
    private String sender;

    @Override
    public void send(EmailMessage message) {

        try {

            String body = templateProcess(message);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");

            helper.setFrom(sender);
            helper.setTo(message.getRecipients().toArray(new String[0]));
            helper.setSubject(message.getSubject());
            helper.setText(body, true);

            mailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new EmailException();
        }

    }

    private String templateProcess(EmailMessage message) {

        try {
            Template template = freemarkerConfig.getTemplate(message.getBody());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template,message.getModels());
        } catch (Exception e) {
            throw new EmailException();
        }
    }
}
