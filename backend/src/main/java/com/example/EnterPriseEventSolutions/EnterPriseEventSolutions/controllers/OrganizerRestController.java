package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.controllers;

import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.Event;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.EventService;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/organizers")
public class OrganizerRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Get organizer's events", description = "Fetches all events organized by the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Events found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/events")
    public ResponseEntity<List<Event>> getMyEvents(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        List<Event> events = eventService.findByUser(user);
        try {
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create a new event", description = "Creates a new event organized by the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Event created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/events")
    public ResponseEntity<Event> postEvents(HttpServletRequest request, @RequestBody Event event) {
        Principal principal = request.getUserPrincipal();
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        event.setOrganization(user);
        if (event.getEncodedPassword() != null) {
            event.setEncodedPassword(passwordEncoder.encode(event.getEncodedPassword()));
            event.setPrivateEvent(true);
        }
        eventService.saveEvent(event);
        try {
            return new ResponseEntity<>(event, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete an event", description = "Deletes the specified event organized by the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Event deleted",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/events/{id}")
    public ResponseEntity<HttpStatus> deleteEvents(HttpServletRequest request, @PathVariable Long id) {
        Principal principal = request.getUserPrincipal();
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        Event event = eventService.findById(id).orElseThrow();
        if (event.getOrganization().getUsername().equals(user.getUsername())) {
            eventService.deleteEvent(event);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update an event", description = "Updates the specified event organized by the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Event updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Event.class))
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/events/{id}")
    public ResponseEntity<Event> updateEvent(HttpServletRequest request, @PathVariable Long id, @RequestBody Event updateEvent) {
        Principal principal = request.getUserPrincipal();
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        Event event = eventService.updateEvent(id, updateEvent);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @Operation(summary = "Get event statistics", description = "Fetches statistics of all events organized by the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Event statistics",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/event/stats")
    public ResponseEntity<Map<String, Integer>> eventsStats(HttpServletRequest request) {
        try {
            Principal principal = request.getUserPrincipal();
            User user = userService.findByEmail(principal.getName()).orElseThrow();
            List<Event> events = eventService.findByUser(user);
            Map<String, Integer> eventStats = new HashMap<>();
            for (Event event : events) {
                int userCount = event.getCurrent_capacity();
                eventStats.put(event.getName(), userCount);
            }
            return new ResponseEntity<>(eventStats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
