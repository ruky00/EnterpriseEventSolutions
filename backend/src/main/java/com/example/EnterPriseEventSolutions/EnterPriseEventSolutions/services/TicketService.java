package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services;



import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.Event;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.Ticket;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.repositories.TicketRepository;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private QRCodeGenerator qrCodeGenerator;

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


    private boolean checkPassword(String encodedPassword, String passwordToCheck){
        return passwordEncoder.matches(passwordToCheck,encodedPassword);
    }

    public Ticket createTicket(User user,Long id,String passwordToCheck) throws IOException, WriterException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Event event = eventService.findById(id).orElseThrow();


        if (!event.isPrivateEvent()){
            eventService.checkIfFull(id);

            Ticket ticket = new Ticket(user,event);
            ticket.setPrice(event.getPrice());
            String formattedDate = dateFormat.format(event.getDate());
            saveTicket(ticket);
            String qrCodeData = "Identificador: " + ticket.getId() + ", Nombre del Evento: " + event.getName() + ", Fecha del evento: "+ formattedDate;
            byte[] qrCodeBytes = qrCodeGenerator.generateQRCode(qrCodeData, 200, 200);
            ticket.setQrCode(qrCodeBytes);
            saveTicket(ticket);

        return ticket ;
        }else{
         if (checkPassword(event.getEncodedPassword(),passwordToCheck)){
             eventService.checkIfFull(id);
             Ticket ticket = new Ticket(user,event);
             ticket.setPrice(event.getPrice());
             String formattedDate = dateFormat.format(event.getDate());
             saveTicket(ticket);
             String qrCodeData = "Identificador: " + ticket.getId() + ", Nombre del Evento: " + event.getName() + ", Fecha del evento: "+ formattedDate;
             byte[] qrCodeBytes = qrCodeGenerator.generateQRCode(qrCodeData, 200, 200);
             ticket.setQrCode(qrCodeBytes);
             saveTicket(ticket);
             return ticket ;
         }
         else
             {return null;}
        }
    }



}
