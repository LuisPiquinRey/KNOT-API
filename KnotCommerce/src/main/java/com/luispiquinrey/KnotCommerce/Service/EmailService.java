package com.luispiquinrey.KnotCommerce.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private final JavaMailSender mailSender;

    EmailService(JavaMailSender mailSender){
        this.mailSender=mailSender;
    }

    public void sendEmail(String to,String subject,String body){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setFrom("piquin.rey@gmail.com");
        message.setText(body);
        mailSender.send(message);
    }
}
