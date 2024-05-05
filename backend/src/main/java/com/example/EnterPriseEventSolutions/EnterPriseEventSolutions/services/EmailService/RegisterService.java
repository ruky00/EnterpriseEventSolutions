package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.EmailService;

import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.ConfirmationToken;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RegisterService {

    @Autowired
    private UserService clientService;

    @Autowired
    ConfirmationTokenService confirmationTokenService;

    public String confirmToken(String token) {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenService.getToken(token);
        if(confirmationToken.isPresent()){
            if(confirmationToken.get().getComfirmTime() != null) {
                throw new IllegalStateException("email already confirm");
            }
            LocalDateTime expiredAt = confirmationToken.get().getExpireAt();

            if(expiredAt.isBefore(LocalDateTime.now())){
                throw new IllegalStateException("token expired");
            }
            confirmationToken.get().setComfirmTime(LocalDateTime.now());
            confirmationTokenService.saveConfirmationToken(confirmationToken.orElseThrow());
            clientService.enableUser(confirmationToken.get().getUser());
            try {
                // Load the email template from the file
                ClassPathResource resource = new ClassPathResource("templates/email-confirmed.html");
                InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
                String emailConfirmed = FileCopyUtils.copyToString(reader);

                return emailConfirmed;
            } catch (IOException e) {
                // Handle the exception (e.g., log an error)
                e.printStackTrace();
                return ""; // Return an empty string or a default email template as a fallback
            }
        }else
            return "Token not found";

    }

    public String buildEmail(String name, String link) {
        try {
            // Load the email template from the file
            ClassPathResource resource = new ClassPathResource("templates/email-template.html");
            InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
            String emailTemplate = FileCopyUtils.copyToString(reader);

            // Replace the placeholders with actual values
            emailTemplate = emailTemplate.replace("{name}", name);
            emailTemplate = emailTemplate.replace("{link}", link);

            return emailTemplate;
        } catch (IOException e) {
            // Handle the exception (e.g., log an error)
            e.printStackTrace();
            return ""; // Return an empty string or a default email template as a fallback
        }
    }

    public String buildEmailOrg(String name, String link, String email) {
        try {
            // Load the email template from the file
            ClassPathResource resource = new ClassPathResource("templates/email-template-org.html");
            InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
            String emailTemplate = FileCopyUtils.copyToString(reader);

            // Replace the placeholders with actual values
            emailTemplate = emailTemplate.replace("{name}", name);
            emailTemplate = emailTemplate.replace("{link}", link);
            emailTemplate = emailTemplate.replace("{email}", email);
            return emailTemplate;
        } catch (IOException e) {
            // Handle the exception (e.g., log an error)
            e.printStackTrace();
            return ""; // Return an empty string or a default email template as a fallback
        }
    }
}
