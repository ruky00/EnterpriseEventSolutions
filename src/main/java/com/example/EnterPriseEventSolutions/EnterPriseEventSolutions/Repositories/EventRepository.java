package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Repositories;

import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.Event;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {

    public Optional<Event> findByName(String name);

    public List<Event> findByUser(User user);

    @Transactional
    @Modifying
    @Query("UPDATE Event e SET e.current_capacity = e.current_capacity + 1 WHERE e.id = :eventId AND e.current_capacity + 1  <= e.max_capacity")
    public int incrementCurrentCapacity(@Param("id") long eventId);

}
