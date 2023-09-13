package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Repositories;

import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

    public Optional<Event> findByName(String name);

}
