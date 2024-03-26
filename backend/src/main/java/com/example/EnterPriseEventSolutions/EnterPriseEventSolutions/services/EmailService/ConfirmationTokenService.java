package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.EmailService;

import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.ConfirmationToken;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.repositories.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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