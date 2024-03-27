package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.controllers;


import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.ConfirmationToken;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.Event;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.UserTipeEnum;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.repositories.ConfirmationTokenRepository;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.EmailService.ConfirmationTokenService;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.EmailService.EmailService;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.EmailService.RegisterService;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.EventService;

import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.Image.ImageService;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import java.nio.file.Files;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private EventService eventService;

    @Autowired
    private boolean isTestEnvironment;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private ImageService imageService;

    //POST members
    @Operation(summary = "Post a new member")

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
                    responseCode = "403",
                    description = "Forbidden",
                    content = @Content
            )
    })
    @JsonView(User.BasicInfo.class)
    @PostMapping("/users/")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createMember(@RequestBody User user) {
        if (isTestEnvironment) {
            user.setEnable(true);
        }
        if(userService.findByEmail(user.getEmail()).isPresent()){
            return new ResponseEntity("User already Exists",HttpStatus.BAD_REQUEST);
        }

        try {
            LocalDateTime currentDate = LocalDateTime.now();
            user.setCreateDateTime(currentDate);
            user.setEncodedPassword(passwordEncoder.encode(user.getEncodedPassword()));
            if(user.getDescription()!=null){
                user.setRole(UserTipeEnum.ORGANIZATION);
            }else{user.setRole(UserTipeEnum.CLIENT);}

            String filePath = "static/images/mujer2.jpg";
            MultipartFile multipartFile = convert(filePath);
            imageService.createImage(multipartFile,"profileImage",user.getUsername());
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
        }catch (Exception e){
            System.out.println(e);  return new ResponseEntity<>(HttpStatus.BAD_REQUEST);}
    }


    private MultipartFile convert(String filePath) throws IOException {
        // Obtiene el archivo de la ruta especificada
        File file = ResourceUtils.getFile("classpath:" + filePath);

        // Lee el contenido del archivo en un array de bytes
        byte[] bytes = Files.readAllBytes(file.toPath());

        // Crea una instancia de MultipartFile utilizando MockMultipartFile
        return new MockMultipartFile(file.getName(), file.getName(),
                Files.probeContentType(file.toPath()), bytes);
    }

    private String getUserProfileImageUrl(String username) {
        // Construyes la URL de la imagen para el usuario
        return "https://" + "enterpriseeventsolutions" + ".s3.eu-west-2.amazonaws.com/" + username + "/profileImage";
    }


    @GetMapping("/users/confirm")
    public String confirm(@RequestParam("token") String token){
        return registerService.confirmToken(token);
    }



    //GET PERSONAL INFORMATION
    @Operation(summary = "Get user logged in app")
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
    @GetMapping("/users/me")
    public ResponseEntity<Optional<User>> getPersonalInfo(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        if (principal!=null){
            return ResponseEntity.ok(userService.findByEmail(principal.getName()));
        }else
            return ResponseEntity.notFound().build();
    }

    //GET PERSONAL INFORMATION
    @Operation(summary = "Get events  in app")
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
    public ResponseEntity<List<Event>> getEventsFromOrg(@RequestParam(name = "org") String org){
        Optional<User> userOrg = userService.findByUsername(org);
        if (userOrg.isPresent() && userOrg.get().getRole()==UserTipeEnum.ORGANIZATION){
            List<Event> event = eventService.findByUser(userOrg.orElseThrow());
            return new ResponseEntity<>(event, HttpStatus.OK);
        }else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get event")
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
    @GetMapping("events/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id){
        Optional<Event> event = eventService.findById(id);
        if (event.isPresent()){
            return new ResponseEntity(event,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Get organizer")
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
    @GetMapping("/organizers/{id}")
    @JsonView(User.OrgInfo.class)
    public ResponseEntity<User> getOrgById(@PathVariable Long id){
        Optional<User> user = userService.findById(id);
        if(user.isPresent()){
            return new ResponseEntity(user,HttpStatus.OK);
        }else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //UPDATE USER
    @Operation(summary = "Update User")
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
    @PutMapping("/users/me/")
    public ResponseEntity<Optional<User>> updateUser(HttpServletRequest request,@RequestBody User updated){
        Principal principal = request.getUserPrincipal();
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        if (!updated.getUsername().equals("")){
            user.setUsername(updated.getUsername());
        }if (!updated.getEmail().equals("")){
            user.setEmail(updated.getEmail());
        }if (!updated.getEncodedPassword().equals("")){
            String pass= passwordEncoder.encode(updated.getEncodedPassword());
            user.setEncodedPassword(pass);
        }

        if (user.getRole()==UserTipeEnum.ORGANIZATION){
            if (!updated.getDescription().equals("")){
                user.setDescription(updated.getDescription());
            }
        }

        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //DELETE USER
    @Operation(summary = "Delete my user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Deleted Complete",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation= User.class)
                    )}
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No Content",
                    content = @Content
            )



    })
    @DeleteMapping("/users/me")
    public ResponseEntity<Optional<User>> deleteUser(HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        User user = userService.findByEmail(principal.getName()).orElseThrow();
        List<ConfirmationToken> tokensToDelete = confirmationTokenRepository.findByUser(user);
        for (ConfirmationToken token : tokensToDelete) {
            
            confirmationTokenRepository.delete(token);
        }
        userService.deleteUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
