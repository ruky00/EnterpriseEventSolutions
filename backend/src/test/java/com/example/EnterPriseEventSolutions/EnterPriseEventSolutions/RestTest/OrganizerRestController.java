package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.RestTest;

import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrganizerRestController extends ControllerRestTest {

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", mySQLContainer::getDriverClassName);
    }

    @DisplayName("Post a new event")
    @WithMockUser(username = "organizer@urjc.es", roles = {"ORGANIZATION"})
    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(strings = {"Evento Posteado en Test"})
    public void postEventTest(String eventName) throws Exception {
        Event event = new Event();
        event.setName(eventName);
        event.setDescription("Descripción del evento");
        event.setDate(new java.util.Date());  // Set an appropriate date
        event.setPrice(0.0);
        event.setMax_capacity(50);

        String eventJson = objectMapper.writeValueAsString(event);

        mockMvc.perform(post("/api/organizers/events/")
                .contentType("application/json")
                .content(eventJson))
                .andExpect(status().isCreated())
                .andDo(print());
    }








    @DisplayName("Delete an Event")
    @WithMockUser(username = "organizer@urjc.es", roles = {"ORGANIZER"})
    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(longs = {3L}) // Assuming the event ID is 1 for this example
    public void deleteEventTest(Long eventId) throws Exception {
        mockMvc.perform(get("/api/organizers/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(eventId)) // Assuming the first event ID is the one we want to delete
                .andDo(result -> {
                    String content = result.getResponse().getContentAsString();
                    System.out.println("Content: " + content); // Optional: Print the response content
                })
                .andDo(print());

        mockMvc.perform(delete("/api/organizers/events/" + eventId))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
