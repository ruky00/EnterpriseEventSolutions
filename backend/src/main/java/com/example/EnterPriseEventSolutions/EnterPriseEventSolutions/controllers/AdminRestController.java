package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.controllers;


import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.ConfirmationToken;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.Event;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.UserTipeEnum;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.EmailService.ConfirmationTokenService;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.EmailService.EmailService;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.EmailService.RegisterService;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.EventService;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.Image.ImageService;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    @Autowired
    private ImageService imageService;

    @Value("${app.base-url}")
    private String baseUrl;

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
    @JsonView(User.BasicInfo.class)
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
            String token = UUID.randomUUID().toString();
            ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
            confirmationTokenService.saveConfirmationToken(confirmationToken);
            String link = baseUrl + ":8443/api/users/confirm?token=" + token;
            emailService.sendOrg(user.getEmail(), registerService.buildEmailOrg(user.getUsername(), link, user.getEmail()));
            URI location = fromCurrentRequest().path("/organizers/{id}")
                    .buildAndExpand(user.getId()).toUri();
            return ResponseEntity.created(location).body(user);

        }catch (Exception e){return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
    }



    @PostMapping("/organizers/{id}/images")
    public ResponseEntity setOrganizerImage(@RequestParam("image")MultipartFile file,
                                            @PathVariable Long id){
        try {
            User user = userService.findById(id).orElseThrow();

            String imageName = imageService.createImage(file,"orgImage",user.getUsername());
            user.setImage(imageName);
            userService.saveUser(user);
            return ResponseEntity.ok("Image uploaded successfully!");

        } catch(Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/organizers/{id}/images/logo")
    public ResponseEntity setOrganizerLogo(@RequestParam("image") MultipartFile logo,@PathVariable Long id){
        try {
            User user = userService.findById(id).orElseThrow();
            String logoName= imageService.createImage(logo,"orgLogo",user.getUsername());
            user.setLogo(logoName);
            userService.saveUser(user);
            return ResponseEntity.ok("Logo uploaded successfully!");

        } catch(Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
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
    @DeleteMapping("/users/{id}")
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
    @Operation(summary = "Get Users per month")
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
    public ResponseEntity<int[]> usersPerMonth(){
        try {
            List<User> userList = userService.findAll();
           int[] usersByMonth =  userService.getUsersCountByMonth(userList);
            return new ResponseEntity(usersByMonth,HttpStatus.OK);
        }catch (Exception e)
            {return  new ResponseEntity<>(HttpStatus.NOT_FOUND);}

    }

    @GetMapping("/users/graphics/events")
    public ResponseEntity<int[]> eventsPerMonth(){
        try {
            List<Event> eventList = eventService.findAll();
          int[] eventsByMonth =  userService.getEventsCountByMonth(eventList);
            return new ResponseEntity(eventsByMonth,HttpStatus.OK);
        }catch (Exception e)
        {return  new ResponseEntity<>(HttpStatus.NOT_FOUND);}

    }

    //GET USERS PER ROLE
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



