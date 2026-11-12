package com.api_food.Algaworks_Food.infra.service.email;

import com.api_food.Algaworks_Food.domain.exception.custom.EmailException;
import freemarker.template.Configuration;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SandboxEmailSendService extends SmtpEmailSendService {

    private final String sandboxRecipient;

    public SandboxEmailSendService(Configuration freemarkerConfig,
                                   JavaMailSender mailSender,
                                   String sender,
                                   String sandboxRecipient) {
        super(freemarkerConfig, mailSender, sender);
        this.sandboxRecipient = sandboxRecipient;
    }

    @Override
    protected MimeMessage createMimeMessage(EmailMessage message) throws MessagingException {
        if (sandboxRecipient == null || sandboxRecipient.isBlank()) {
            throw new EmailException("EMAIL_DEV_TEST must be configured for sandbox email sending");
        }

        MimeMessage mimeMessage = super.createMimeMessage(message);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setTo(sandboxRecipient);
        return mimeMessage;
    }
}
