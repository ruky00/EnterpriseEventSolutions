package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Repositories;

import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    public Optional<User> findByEmail(String email);


}
