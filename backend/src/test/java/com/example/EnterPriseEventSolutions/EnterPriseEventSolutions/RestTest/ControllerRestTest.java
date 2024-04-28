package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.RestTest;


import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.Image.ImageService;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
public class ControllerRestTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @LocalServerPort
    int port;

    @Autowired
    protected ObjectMapper objectMapper;

    @Container
    public static MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:8.0.24")
            .withDatabaseName("test")
            .withUsername("user")
            .withPassword("pass");


    protected static User organizer;
    protected static User client;

    @BeforeEach
    protected void setUp() {
        RestAssured.port = port;
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://localhost:" + port;


        // CREATE CLIENT
        client = new User("juan", "admin@urjc.es", "pass");
        userService.saveUser(client);
        // CREATE ORGANIZER
        organizer = new User("Jose", "francisco.gortazar@urjc.es", "pass", "Zara and Associates");
        userService.saveUser(organizer);
    }





}
