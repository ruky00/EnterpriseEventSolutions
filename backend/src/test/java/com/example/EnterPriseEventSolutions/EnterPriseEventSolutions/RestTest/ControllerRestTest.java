package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.RestTest;

import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.Event;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.EventService;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.Image.ImageService;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
public class ControllerRestTest{

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private EventService eventService;

    @LocalServerPort
    int port;

    @Autowired
    protected ObjectMapper objectMapper;

    @Container
    public static MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:8.0.28")
            .withDatabaseName("test")
            .withUsername("user")
            .withPassword("pass");

    protected MockMvc mockMvc;

    protected static User organizer;
    protected static User client;
    protected static Event event2;
    protected static Event event3;

    @BeforeEach
    protected void setUp() throws Exception {
        System.out.println(userService.findAll());
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
        if(userService.findById(1L).isEmpty()){
            // CREATE CLIENT
            client = new User("Pepito", "client@urjc.es", "pass");
            userService.saveUser(client);

            // CREATE ORGANIZER
            organizer = new User("Menganito", "organizer@urjc.es", "pass", "Zara and Associates");
            userService.saveUser(organizer);

            // CREATE EVENT
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(2025, Calendar.AUGUST, 5, 18, 30);
            event2 = new Event("Seminario de Tecnología", "Últimas tendencias tecnológicas", calendar2.getTime(), 0.0, 50);
            event2.setOrganization(organizer);
            eventService.saveEvent(event2);

            Calendar calendar3 = Calendar.getInstance();
            calendar3.set(2025, Calendar.AUGUST, 5, 18, 30);
            event3 = new Event("Seminario de Tecnología", "Últimas tendencias tecnológicas", calendar3.getTime(), 0.0, 50);
            event3.setOrganization(organizer);
            eventService.saveEvent(event3);

            // SIGN UP CLIENT TO EVENT
            // Simulate client signing up for the event
            mockMvc.perform(post("/api/clients/tickets/?id=" + event2.getId())
                    .with(user(client.getEmail()).password(client.getEncodedPassword())))
                    .andExpect(status().isCreated());
        }
        else{
            System.out.println("Going on");
        }

    }
}
