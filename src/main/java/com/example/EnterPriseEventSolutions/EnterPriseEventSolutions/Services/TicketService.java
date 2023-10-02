package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Services;



import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.Event;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.Ticket;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    public Optional<Ticket> findById(long id){
        return ticketRepository.findById(id);
    }

    public List<Ticket> findAll(){
        return ticketRepository.findAll();
    }

    public void saveTicket(Ticket ticket){
        ticketRepository.save(ticket);
    }

    public void deleteTicket(Ticket ticket){
        ticketRepository.delete(ticket);
    }

    public Ticket createTicket(User user,Long id){

        Event event = eventService.findById(id).orElseThrow();

        eventService.checkIfFull(id);

        Ticket ticket = new Ticket(user,event);
        ticket.setPrice(event.getPrice());
        saveTicket(ticket);

        return ticket ;
    }



}
