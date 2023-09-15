package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Services;



import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.Ticket;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Optional<Ticket> findById(long id){
        return ticketRepository.findById(id);
    }

    public List<Ticket> findAll(){
        return ticketRepository.findAll();
    }

    public void saveTicket(Ticket user){
        ticketRepository.save(user);
    }

    public void deleteTicket(Ticket user){
        ticketRepository.delete(user);
    }


}
