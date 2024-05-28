package com.example.apiWithDb.service;

import com.example.apiWithDb.dto.MailBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    private JavaMailSender mailSender;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        mailSender = mock(JavaMailSender.class);
        emailService = new EmailService(mailSender);
    }

    @Test
    void testSendSimpleMessage_Success() {
        // Arrange
        MailBody mailBody = new MailBody("recipient@example.com", "Test Subject", "Test Message");

        // Act
        emailService.sensSimpleMessage(mailBody);

        // Assert
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertNotNull(sentMessage);
        assertEquals("recipient@example.com", Objects.requireNonNull(sentMessage.getTo())[0]);
        assertEquals("viewerstuff@gmail.com", sentMessage.getFrom());
        assertEquals("Test Subject", sentMessage.getSubject());
        assertEquals("Test Message", sentMessage.getText());
    }


    @Test
    void testSendSimpleMessage_EmptySubject() {
        // Arrange
        MailBody mailBody = new MailBody("recipient@example.com", "", "Test Message");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> emailService.sensSimpleMessage(mailBody));
        verifyNoInteractions(mailSender);
    }

    @Test
    void testSendSimpleMessage_EmptyMessage() {
        // Arrange
        MailBody mailBody = new MailBody("recipient@example.com", "Test Subject", "");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> emailService.sensSimpleMessage(mailBody));
        verifyNoInteractions(mailSender);
    }
}