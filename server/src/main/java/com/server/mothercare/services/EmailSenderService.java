package com.server.mothercare.services;

import com.server.mothercare.entities.ConfirmationToken;
import com.server.mothercare.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("emailSenderService")
public class EmailSenderService {
    private JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    public ConfirmationToken confirm(String email, User theUser) {
        ConfirmationToken confirmationToken = new ConfirmationToken(theUser);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("abdullaelsayed167@yahoo.com");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());
        sendEmail(mailMessage);
        return confirmationToken;
    }
}
