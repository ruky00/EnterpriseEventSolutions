package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Repositories;

import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.ConfirmationToken;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Long> {

    Optional<ConfirmationToken> findByConfirmationToken(String token);
    List<ConfirmationToken> findByUser(User user);
}