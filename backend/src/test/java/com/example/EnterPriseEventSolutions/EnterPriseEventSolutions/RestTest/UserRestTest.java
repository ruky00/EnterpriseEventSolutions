package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.RestTest;

import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.repositories.UserRepository;


import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.security.jwt.AuthResponse;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.EmailService.EmailService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;


public class UserRestTest extends ControllerRestTest {

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private EmailService emailService;


    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", mySQLContainer::getDriverClassName);
    }

    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(strings = { "Customer" })
    @DisplayName("A user can register, login and access to himself")
    public void deleteUserTest(String type) throws Exception {

        // CREATE NEW USER and automatically activate for testing
        ObjectNode user = objectMapper.createObjectNode()
                .put("username", "ToDeleteUser_" + type)
                .put("email", type + "_delete@urjc.es")
                .put("encodedPassword", "pass");
                 // Activate user directly

        given()
                .request()
                .body(user)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/users/")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("username", equalTo(user.get("username").asText()))
                .extract().as(User.class);

        // Simulate email sent (optional)


        //LOGIN
        ObjectNode loginRequest = objectMapper.createObjectNode()
                .put("username", user.get("email").asText())
                .put("password", user.get("encodedPassword").asText());
        AuthResponse response = given()
                .contentType("application/json")
                .body(loginRequest)
                .contentType(ContentType.JSON)
                .when()
                .post("/api/auth/login")
                .then()
                .assertThat()
                .body("status", equalTo(AuthResponse.Status.SUCCESS.toString()))
                .statusCode(200)
                .extract()
                .as(AuthResponse.class);
    }
}
