package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.controllers;


import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.Ticket;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.UserTipeEnum;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.TicketService;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientRestController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    //BUY A TICKET
    @Operation(summary = "Buy a ticket")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Ticked buyed",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation= User.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = @Content
            )
    })
    @PostMapping("/tickets/")
    public ResponseEntity<Ticket>  buyTicket(HttpServletRequest request, @RequestParam Long id,@RequestBody(required = false)  String password){
        Principal principal = request.getUserPrincipal();
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        String passwordSent;
        try {
            if (password == null){passwordSent="";}else{passwordSent=password;}
            Ticket ticket = ticketService.createTicket(user,id,passwordSent);
            if(ticket!=null)
            return new ResponseEntity<>(ticket,HttpStatus.CREATED);

        }catch (Exception e){return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);}
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    //GET MY TICKETS
    @Operation(summary = "Get my ticket")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Get Tickets",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation= User.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = @Content
            )
    })
    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getMyTickets(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        List<Ticket> tickets = user.getTickets();
        return new ResponseEntity<>(tickets,HttpStatus.OK);

    }

    @GetMapping("/tickets/{id}")
    public ResponseEntity<Optional<Ticket>> getTicketById(@PathVariable long id){
        try {
            Ticket ticket = ticketService.findById(id).orElseThrow();
            return new ResponseEntity(ticket, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @JsonView(User.OrgInfo.class)
    @GetMapping("/organizers")
    public ResponseEntity<List<User>> getCompanies(){
        List<User> companies = userService.findAllByRole(UserTipeEnum.ORGANIZATION);
        return new ResponseEntity<>(companies,HttpStatus.OK);
    }

}
