package com.api_food.Algaworks_Food.infra.service.email;

import com.api_food.Algaworks_Food.config.EmailConfig;
import com.api_food.Algaworks_Food.domain.exception.custom.EmailException;
import com.api_food.Algaworks_Food.domain.service.EmailSendService;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class SandboxEmailSendServiceTest {

    private Configuration createFreemarkerConfig() {
        Configuration config = new Configuration(Configuration.VERSION_2_3_33);
        StringTemplateLoader loader = new StringTemplateLoader();
        loader.putTemplate("test-template.html", "<p>Hello ${name}</p>");
        config.setTemplateLoader(loader);
        return config;
    }

    private JavaMailSender createMockMailSender() {
        JavaMailSender mailSender = mock(JavaMailSender.class);
        when(mailSender.createMimeMessage()).thenAnswer(inv -> new MimeMessage(Session.getInstance(new Properties())));
        return mailSender;
    }

    @Test
    void sandboxRedirectsRecipientsToConfiguredDevEmail() throws Exception {
        JavaMailSender mailSender = createMockMailSender();
        SandboxEmailSendService service = new SandboxEmailSendService(
                createFreemarkerConfig(), mailSender, "no-reply@algafood.com", "dev-test@example.com");

        EmailSendService.EmailMessage message = EmailSendService.EmailMessage.builder()
                .recipient("customer1@example.com")
                .recipient("customer2@example.com")
                .subject("Order #10 Confirmed")
                .body("test-template.html")
                .model("name", "Thalys")
                .build();

        service.send(message);

        ArgumentCaptor<MimeMessage> captor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(mailSender).send(captor.capture());

        MimeMessage sent = captor.getValue();
        assertThat(sent.getFrom()).containsExactly(new InternetAddress("no-reply@algafood.com"));
        assertThat(sent.getRecipients(Message.RecipientType.TO))
                .containsExactly(new InternetAddress("dev-test@example.com"));
        assertThat(sent.getSubject()).isEqualTo("Order #10 Confirmed");
        assertThat(sent.getContent().toString()).isEqualTo("<p>Hello Thalys</p>");
    }

    @Test
    void smtpKeepsOriginalRecipients() throws Exception {
        JavaMailSender mailSender = createMockMailSender();
        SmtpEmailSendService service = new SmtpEmailSendService(
                createFreemarkerConfig(), mailSender, "no-reply@algafood.com");

        EmailSendService.EmailMessage message = EmailSendService.EmailMessage.builder()
                .recipient("customer@example.com")
                .subject("Order #11 Confirmed")
                .body("test-template.html")
                .model("name", "Thalys")
                .build();

        service.send(message);

        ArgumentCaptor<MimeMessage> captor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(mailSender).send(captor.capture());

        MimeMessage sent = captor.getValue();
        assertThat(sent.getRecipients(Message.RecipientType.TO))
                .containsExactly(new InternetAddress("customer@example.com"));
    }

    @Test
    void sandboxThrowsEmailExceptionWhenDevEmailIsBlank() {
        JavaMailSender mailSender = createMockMailSender();
        SandboxEmailSendService service = new SandboxEmailSendService(
                createFreemarkerConfig(), mailSender, "no-reply@algafood.com", "  ");

        EmailSendService.EmailMessage message = EmailSendService.EmailMessage.builder()
                .recipient("customer@example.com")
                .subject("Order #12 Confirmed")
                .body("test-template.html")
                .model("name", "Thalys")
                .build();

        assertThatThrownBy(() -> service.send(message))
                .isInstanceOf(EmailException.class)
                .hasMessageContaining("EMAIL_DEV_TEST");
        verify(mailSender, never()).send(any(MimeMessage.class));
    }

    @Test
    void emailConfigSelectsExpectedImplementationByProperty() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withUserConfiguration(EmailConfig.class)
                .withBean(Configuration.class, this::createFreemarkerConfig)
                .withBean(JavaMailSender.class, this::createMockMailSender)
                .withPropertyValues(
                        "MAIL_SENDER=no-reply@algafood.com",
                        "EMAIL_DEV_TEST=dev-test@example.com");

        contextRunner.withPropertyValues("algafood.mail.impl=sandbox")
                .run(ctx -> assertThat(ctx.getBean(EmailSendService.class))
                        .isInstanceOf(SandboxEmailSendService.class));

        contextRunner.withPropertyValues("algafood.mail.impl=fake")
                .run(ctx -> assertThat(ctx.getBean(EmailSendService.class))
                        .isInstanceOf(FakeEmailSendService.class));

        contextRunner.withPropertyValues("algafood.mail.impl=smtp")
                .run(ctx -> assertThat(ctx.getBean(EmailSendService.class))
                        .isExactlyInstanceOf(SmtpEmailSendService.class));
    }
}
