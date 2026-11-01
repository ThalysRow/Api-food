package com.api_food.Algaworks_Food.config;

import com.api_food.Algaworks_Food.domain.service.EmailSendService;
import com.api_food.Algaworks_Food.infra.service.email.FakeEmailSendService;
import com.api_food.Algaworks_Food.infra.service.email.SmtpEmailSendService;
import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;

@org.springframework.context.annotation.Configuration
public class EmailConfig {

    @Bean
    @ConditionalOnProperty(name = "algafood.mail.impl", havingValue = "smtp", matchIfMissing = true)
    public EmailSendService smtpEmailSendService(Configuration freemarkerConfig,
                                                  JavaMailSender mailSender,
                                                  @Value("${MAIL_SENDER}") String sender,
                                                  @Value("${EMAIL_DEV_TEST}") String emailDevTest) {
        return new SmtpEmailSendService(freemarkerConfig, mailSender, sender, emailDevTest);
    }

    @Bean
    @ConditionalOnProperty(name = "algafood.mail.impl", havingValue = "fake")
    public EmailSendService fakeEmailSendService(Configuration freemarkerConfig) {
        return new FakeEmailSendService(freemarkerConfig);
    }
}
