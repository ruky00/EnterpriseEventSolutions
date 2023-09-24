package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Services.EmailService;

import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.ConfirmationToken;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Repositories.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository tokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken){
        tokenRepository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> getToken(String token){
        return tokenRepository.findByConfirmationToken(token);
    }
}