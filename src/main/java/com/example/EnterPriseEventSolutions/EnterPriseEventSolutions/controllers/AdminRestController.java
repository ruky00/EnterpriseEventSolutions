package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.controllers;


import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.ConfirmationToken;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.Event;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.UserTipeEnum;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.EmailService.ConfirmationTokenService;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.EmailService.EmailService;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.EmailService.RegisterService;
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

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RegisterService registerService;

//GET ALL USERS
    @Operation(summary = "Get Users")
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
                    responseCode = "204",
                    description = "No content",
                    content = @Content
            )
    })
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userList = userService.findAll();
        if (userList.size()>0) {
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }else
            return new ResponseEntity("No Users in the system",HttpStatus.NOT_FOUND);
    }


    @Operation(summary = "Get Events")
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
                    responseCode = "204",
                    description = "No content",
                    content = @Content
            )
    })
    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents(){
        try {
            List<Event> events= eventService.findAll();
            return new ResponseEntity<>(events,HttpStatus.OK);
        }catch (Exception e){return new ResponseEntity<>(HttpStatus.NOT_FOUND);}


    }


//POST ORGANIZERS
    @Operation(summary = "Post Organzers")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation= User.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content
            )
    })
    @PostMapping("/organizers/")
    public ResponseEntity<User> postOrganizer(@RequestBody User user){
        try {
            if(userService.findByEmail(user.getEmail()).isPresent()){
                return new ResponseEntity("User already Exists",HttpStatus.BAD_REQUEST);
            }
            LocalDateTime currentDate = LocalDateTime.now();
            user.setCreateDateTime(currentDate);
            user.setEncodedPassword(passwordEncoder.encode(user.getEncodedPassword()));
            user.setRole(UserTipeEnum.ORGANIZATION);
            userService.saveUser(user);
            user.setImage(getUserProfileImageUrl(user.getUsername()));
            userService.saveUser(user);
            String token = UUID.randomUUID().toString();
            ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
            confirmationTokenService.saveConfirmationToken(confirmationToken);
            String link = "https://localhost:8443/api/users/confirm?token=" + token;
            emailService.send(user.getEmail(), registerService.buildEmail(user.getUsername(), link));
            URI location = fromCurrentRequest().path("/users/{id}")
                    .buildAndExpand(user.getId()).toUri();
            return ResponseEntity.created(location).body(user);

        }catch (Exception e){return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
    }

    private String getUserProfileImageUrl(String username) {
        // Construyes la URL de la imagen para el usuario
        return "https://" + "enterpriseeventsolutions" + ".s3.eu-west-2.amazonaws.com/" + username + "/profileImage";
    }


//DELETE ORGANIZERS
    @Operation(summary = "DELETE Organizers")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Deleted",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation= User.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = @Content
            )
    })
    @DeleteMapping("/organizers/{id}")
    public ResponseEntity<User> deleteOrganizers(@PathVariable Long id){
        Optional<User> user = userService.findById(id);
        if (user.isPresent()){
            userService.deleteUser(user.orElseThrow());
            return  new ResponseEntity("User with id:"+id+"was deleted",HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//GET USERS GRAPHICS
    @Operation(summary = "Get Events per month")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "found",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation= Event.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = @Content
            )
    })
    @GetMapping("/users/graphics/users")
    public ResponseEntity<Map<String,Integer>> usersPerMonth(){
        try {
            List<User> userList = userService.findAll();
            Map<String,Integer> usersByMonth =  userService.getUsersCountByMonth(userList);
            return new ResponseEntity<>(usersByMonth,HttpStatus.OK);
        }catch (Exception e)
            {return  new ResponseEntity<>(HttpStatus.NOT_FOUND);}

    }

    @GetMapping("/users/graphics/events")
    public ResponseEntity<Map<String,Integer>> eventsPerMonth(){
        try {
            List<Event> eventList = eventService.findAll();
            Map<String,Integer> eventsByMonth =  userService.getEventsCountByMonth(eventList);
            return new ResponseEntity<>(eventsByMonth,HttpStatus.OK);
        }catch (Exception e)
        {return  new ResponseEntity<>(HttpStatus.NOT_FOUND);}

    }

    //GET COUNT GRAPHICS
    @Operation(summary = "Get Users per Type")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "found",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation= User.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not Found",
                    content = @Content
            )
    })
    @GetMapping("/users/roles")
    public ResponseEntity<Map<String,Integer>> countForType(){
        try{
            Map<String,Integer> userCount = new HashMap<>();
            List<User> clients = userService.findAllByRole(UserTipeEnum.CLIENT);
            List<User> org = userService.findAllByRole(UserTipeEnum.ORGANIZATION);
            userCount.put(UserTipeEnum.CLIENT.toString(), clients.size());
            userCount.put(UserTipeEnum.ORGANIZATION.toString(), org.size());
            return new ResponseEntity<>(userCount, HttpStatus.OK);
        }catch (Exception e){return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
    }




}



