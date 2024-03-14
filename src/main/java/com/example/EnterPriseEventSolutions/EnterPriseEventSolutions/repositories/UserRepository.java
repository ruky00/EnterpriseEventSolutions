package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.repositories;

import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.UserTipeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    public Optional<User> findByEmail(String email);

    public List<User> findAllByRole(UserTipeEnum type);

    public Optional<User> findByUsername(String username);
}
