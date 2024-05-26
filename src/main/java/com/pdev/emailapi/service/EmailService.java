package com.pdev.emailapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {


    @Value("${email.username}")
    private String from;

    @Value("${email.password}")
    private String password;

    public boolean sendEmail(String subject, String message, String to) {
        String host = "smtp.gmail.com";
        boolean success = false;

        // Get the system properties
        Properties properties = new Properties();
        System.out.println("PROPERTIES " + properties);

        // Setting up mail server properties
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object and pass username and password
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object
            MimeMessage mimeMessage = new MimeMessage(session);

            // Set From: header field
            mimeMessage.setFrom(new InternetAddress(from));

            // Set To: header field
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            mimeMessage.setSubject(subject);

            // Now set the actual message
            mimeMessage.setText(message);

            // Send message
            Transport.send(mimeMessage);
            System.out.println("Sent message successfully....");
            success = true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return success;
    }
}
