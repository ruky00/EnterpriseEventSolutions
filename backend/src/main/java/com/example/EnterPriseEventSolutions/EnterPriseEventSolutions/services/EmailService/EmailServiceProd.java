package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.EmailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service("EmailService")
@Profile("prod")
public class EmailServiceProd implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailServiceProd.class);



    @Async
    @Override
    public void send(String to, String email){
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your Account");
            helper.setFrom("enterpriseeventsolutioninfo@gmail.com");
            javaMailSender.send(mimeMessage);
        }catch (MessagingException e){
            LOGGER.error("Failed to send email", e);
            throw new IllegalStateException("Fail to send email");
        }



    }
    @Async
    @Override
    public void sendOrg(String to, String email){
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your Account");
            helper.setFrom("enterpriseeventsolutioninfo@gmail.com");
            javaMailSender.send(mimeMessage);
        }catch (MessagingException e){
            LOGGER.error("Failed to send email", e);
            throw new IllegalStateException("Fail to send email");
        }



    }
}