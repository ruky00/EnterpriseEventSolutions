package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.EmailService;

import java.util.ArrayList;
import java.util.List;

public class MockEmailService extends EmailService {
    private final List<String> sentEmails = new ArrayList<>();

    public void sendActivationEmail(String recipient, String token) {
        sentEmails.add("Activation email sent to: " + recipient + " with token: " + token);
        System.out.println(sentEmails.get(sentEmails.size() - 1)); // Imprime información del correo simulado
    }

    public List<String> getSentEmails() {
        return sentEmails; // Devuelve la lista de correos simulados enviados
    }
}