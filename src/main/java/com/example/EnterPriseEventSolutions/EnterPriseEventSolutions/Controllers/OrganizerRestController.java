package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Controllers;


import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.Event;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Services.EventService;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/organizers")
public class OrganizerRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private EventService eventService;


    //GET ORG EVENTS
    @Operation(summary = "Get org's events")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Source Found",
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
    @GetMapping("/events")
    public ResponseEntity<List<Event>> getMyEvents(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        List<Event> events = eventService.findByUser(user);
        try{
            return new ResponseEntity<>(events,HttpStatus.OK);

        }catch (Exception e){return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);}
    }

    //POST EVENTS
    @Operation(summary = "Post events")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Source Created",
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
                    responseCode = "500",
                    description = "Internal Error",
                    content = @Content
            )
    })
    @PostMapping("/events/")
    public ResponseEntity<Event> postEvents(HttpServletRequest request,Event event) {
        Principal principal = request.getUserPrincipal();
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        event.setOrganization(user);
        eventService.saveEvent(event);
        try {
            return new ResponseEntity<>(event,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //DELETE EVENT
    @Operation(summary = "delete event")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Event Deleted",
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
    @DeleteMapping("/events/{id}")
    public ResponseEntity<HttpStatus> deleteEvents(HttpServletRequest request,@RequestParam long id){
        Principal principal = request.getUserPrincipal();
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        Event event = eventService.findById(id).orElseThrow();
        if(event.getOrganization().getUsername().equals(user.getUsername())){
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update Event")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Event Deleted",
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
    @PutMapping("/events/{id}/")
    public ResponseEntity<Event> updateEvent(HttpServletRequest request,@PathVariable    long id, @RequestBody Event updateEvent) {
        Principal principal = request.getUserPrincipal();
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        Event event = eventService.updateEvent(updateEvent);
        return  new ResponseEntity<>(event,HttpStatus.CREATED);

    }
}
