package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.repositories;


import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {

}
