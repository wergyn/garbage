package com.training.utils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;

@Component
@PropertySource("classpath:mail.properties")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MailSender {

    final JavaMailSender javaMailSender;

    final SpringTemplateEngine templateEngine;

    @Value("${mail.username}")
    String emailSender;

    @SneakyThrows
    public void sendMail(Context context, String templateName, String recipients, String subject) {
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setFrom(emailSender);
        message.setTo(recipients);
        message.setSubject(subject);

        final String htmlContent = templateEngine.process("emails/" + templateName, context);
        message.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
    }
}
