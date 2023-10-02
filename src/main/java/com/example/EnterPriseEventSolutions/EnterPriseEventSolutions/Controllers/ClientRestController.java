package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Controllers;


import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.Ticket;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Services.TicketService;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Services.UserService;
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
    public ResponseEntity<Ticket>  buyTicket(HttpServletRequest request, @RequestParam Long id){
        Principal principal = request.getUserPrincipal();
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        try {

            Ticket ticket = ticketService.createTicket(user,id);
            return new ResponseEntity<>(ticket,HttpStatus.CREATED);

        }catch (Exception e){return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);}
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







}
