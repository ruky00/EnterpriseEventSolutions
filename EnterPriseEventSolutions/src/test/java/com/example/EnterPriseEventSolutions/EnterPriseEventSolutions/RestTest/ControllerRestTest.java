package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.RestTest;


import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.UserTipeEnum;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
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
        RestAssured.baseURI = "http://localhost:" + port;


        // CREATE CLIENT
        client = new User("admin", "admin@urjc.es", "pass");
        userService.saveUser(client);
        // CREATE ORGANIZER
        organizer = new User("Patxi", "francisco.gortazar@urjc.es", "pass", "Zara and Associates");
        userService.saveUser(organizer);
    }





}
