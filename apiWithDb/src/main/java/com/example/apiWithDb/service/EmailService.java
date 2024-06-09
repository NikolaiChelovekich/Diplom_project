package com.example.apiWithDb.service;

import com.example.apiWithDb.dto.MailBody;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    public void sensSimpleMessage(MailBody mailBody) {

        if (mailBody.text() == null || mailBody.text().isEmpty()) {
            throw new IllegalArgumentException("Message body cannot be empty");
        }
        if (mailBody.subject().isEmpty()) {
            throw new IllegalArgumentException("Subject cannot be empty");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailBody.to());
        message.setFrom("viewerstuff@gmail.com");
        message.setSubject(mailBody.subject());
        message.setText(mailBody.text());

        javaMailSender.send(message);
    }
}


