package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.RestTest;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.Event;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.EventService;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Calendar;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.Calendar;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@Testcontainers
public class ClientRestTest extends ControllerRestTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

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

    @DisplayName("Concurrent Ticket Purchase Test")
    @ParameterizedTest(name = "{index} {0}")
    @ValueSource(strings = {"client@urjc.es"})
    public void concurrentTicketPurchaseTest(String username) throws Exception {
        Event testEvent = new Event();
        testEvent.setName("Test Event");
        testEvent.setMax_capacity(10);
        testEvent.setCurrent_capacity(0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.AUGUST, 5, 18, 30);
        testEvent.setDate(calendar.getTime());
        testEvent.setOrganization(organizer);
        eventService.saveEvent(testEvent);

        int numberOfThreads = 15; // More than max capacity to test concurrency issues
        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(numberOfThreads);

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            int userIndex = i; // Final variable to use inside the lambda
            executorService.execute(() -> {
                try {
                    // Creating a unique client user for each thread
                    String uniqueUsername = username + userIndex;
                    User client = new User("Client", uniqueUsername, "pass");
                    userService.saveUser(client);

                    System.out.println("User created: " + uniqueUsername);

                    latch.await(); // wait for the starting signal

                    mockMvc.perform(post("/api/clients/tickets/?id=" + testEvent.getId())
                            .with(SecurityMockMvcRequestPostProcessors.user(client.getEmail()).password("pass"))) // Simulating authenticated user
                            .andExpect(result -> {
                                int status = result.getResponse().getStatus();
                                if (status == HttpStatus.SC_CONFLICT) {
                                    assertThat(status).isEqualTo(HttpStatus.SC_CONFLICT);
                                } else {
                                    assertThat(status).isEqualTo(HttpStatus.SC_CREATED);
                                }
                            })
                            .andDo(print());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error in thread: " + userIndex + " with message: " + e.getMessage());
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        latch.countDown(); // Start all threads
        doneLatch.await(); // Wait for all threads to finish

        // Verify the number of tickets created
        Event updatedEvent = eventService.findById(testEvent.getId()).orElseThrow();
        assertThat(updatedEvent.getCurrent_capacity()).isEqualTo(testEvent.getMax_capacity());

        executorService.shutdown();
    }
}
