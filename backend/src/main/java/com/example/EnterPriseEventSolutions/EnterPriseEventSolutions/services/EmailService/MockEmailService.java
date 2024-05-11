package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.EmailService;

import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;


@Service("EmailService")
@Profile({"dev", "test"})
public class MockEmailService implements EmailService{

    private final List<String> sentEmails = new ArrayList<>();

    @Autowired
    private UserService userService;

    @Override
    public void send(String to, String email) {
        sentEmails.add("Activation email sent to: " + to + "Automatically enabled. This simulates an acceptance to the mail");
        System.out.println(sentEmails.get(sentEmails.size() - 1));
        User user = userService.findByEmail(to).orElseThrow();
        userService.enableUser(user);
        userService.saveUser(user);
    }

    @Override
    public void sendOrg(String to, String email){
        sentEmails.add("Activation email sent to: " + to + "Automatically enabled. This simulates an acceptance to the mail");
        System.out.println(sentEmails.get(sentEmails.size() - 1));
        User user = userService.findByEmail(to).orElseThrow();
        userService.enableUser(user);
        userService.saveUser(user);//
    }
}