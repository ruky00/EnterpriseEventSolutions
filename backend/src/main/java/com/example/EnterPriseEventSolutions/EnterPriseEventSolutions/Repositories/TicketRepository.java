package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Repositories;


import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {

}
