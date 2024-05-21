
package com.gjevents.usermanagementservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationCode(String email, String verificationCode) {
        try {
            // Vérifiez si l'adresse e-mail est null ou vide
            if (email == null || email.isEmpty()) {
                System.out.println("L'adresse e-mail est invalide.");
                return;
            }

            // Validez l'adresse e-mail avec une expression régulière simple
            if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                System.out.println("L'adresse e-mail n'est pas valide : " + email);
            }

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Code de vérification pour votre inscription");
            message.setText("Votre code de vérification est : " + verificationCode);
            mailSender.send(message);
        } catch (MailException e) {
            // Gestion des erreurs d'envoi de l'e-mail
            e.printStackTrace();
        }
    }

    public void sendEmail(String email, String subject, String body) {
        try {
            // Créez un message avec le sujet et le corps fournis
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(subject);
            message.setText(body);

            // Envoyez le message
            mailSender.send(message);
        } catch (MailException e) {
            // Gestion des erreurs d'envoi de l'e-mail
            e.printStackTrace();
        }
    }
}
