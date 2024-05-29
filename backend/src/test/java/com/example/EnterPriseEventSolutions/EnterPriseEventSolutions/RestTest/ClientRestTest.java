package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.RestTest;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
public class ClientRestTest extends ControllerRestTest {

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", mySQLContainer::getDriverClassName);
    }


    @DisplayName("Sign up for an Event")
    @WithMockUser(username = "client@urjc.es", roles = {"CLIENT"})
    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(longs = {3L}) // Assuming the event ID is 1 for this example
    public void signUpForEventTest(Long eventId) throws Exception {
        mockMvc.perform(post("/api/clients/tickets/?id=" + eventId))
                .andExpect(status().isCreated())
                .andDo(print());
    }


    @DisplayName("View User Tickets")
    @WithMockUser(username = "client@urjc.es", roles = {"CLIENT"})
    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(strings = {"client@urjc.es"})
    public void viewUserTicketsTest(String username) throws Exception {
        mockMvc.perform(get("/api/clients/tickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()) // Assuming the response is a JSON array of tickets
                .andDo(print());
    }

    @DisplayName("View User Organizations")
    @WithMockUser(username = "client@urjc.es", roles = {"CLIENT"})
    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(strings = {"client@urjc.es"})
    public void viewUserOrganizationsTest(String username) throws Exception {
        mockMvc.perform(get("/api/clients/organizers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()) // Assuming the response is a JSON array of organizations
                .andDo(print());
    }

}
