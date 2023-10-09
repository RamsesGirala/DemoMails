package com.example.demomails.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String to, String subject, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(message);
        mailSender.send(mail);
    }
    public void sendMailWithAttachment(String to, String subject, String message, String pdfBase64, String docName) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(message, true); // El segundo parámetro indica que el mensaje es HTML
        // Decodifica el archivo PDF desde Base64
        byte[] pdfBytes = Base64.decodeBase64(pdfBase64);
        // Adjunta el archivo PDF al correo electrónico
        helper.addAttachment(docName, new ByteArrayResource(pdfBytes));
        // Envía el correo electrónico
        mailSender.send(mimeMessage);
    }
}