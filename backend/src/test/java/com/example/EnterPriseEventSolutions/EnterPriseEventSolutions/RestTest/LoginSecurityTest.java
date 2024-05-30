package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.RestTest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@ActiveProfiles("test")
public class LoginSecurityTest extends ControllerRestTest {

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", mySQLContainer::getDriverClassName);
    }

    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(strings = {"org"})
    @DisplayName("Check that a non-register user can create an Organizer/Client User")
    public void createUserTest(String type) throws Exception {
        // CREATE NEW USER
        ObjectNode user = objectMapper.createObjectNode()
                .put("username", "NewUser_" + type)
                .put("email", type + "@urjc.es")
                .put("encodedPassword", "pass")
                .put("description", "");

        mockMvc.perform(post("/api/users/")
                .contentType("application/json")
                .content(user.toString()))
                .andExpect(status().is(HttpStatus.SC_CREATED))
                .andDo(print());
    }

    @DisplayName("User Creation and Login Test")
    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(strings = {"org"}) // Test with different user types if needed
    public void loginUserTest(String type) throws Exception {
        // CREATE NEW USER
        ObjectNode user = objectMapper.createObjectNode()
                .put("username", "ToDeleteUser_" + type)
                .put("email", type + "_delete@urjc.es")
                .put("encodedPassword", "pass");

        // Create the user
        mockMvc.perform(post("/api/users/")
                .contentType("application/json")
                .content(user.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(user.get("username").asText()))
                .andDo(print());

        // LOGIN
        ObjectNode loginRequest = objectMapper.createObjectNode()
                .put("username", user.get("email").asText())
                .put("password", user.get("encodedPassword").asText());

        mockMvc.perform(post("/api/auth/login")
                .contentType("application/json")
                .content(loginRequest.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andDo(print());
    }




}
