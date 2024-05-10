package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.UnitaryTest;


import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.DataBaseInitializer;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.Event;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.UserTipeEnum;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.repositories.EventRepository;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.repositories.UserRepository;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.EventService;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.Image.ImageService;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Event Features Unitary tests - MockedMVC")
public class EventTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DataBaseInitializer dataBaseInitializer;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private EventService eventService;

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    private ImageService imageService;


    private Event event1;
    private Event event2;
    private User organizer;
    private User client;

    @BeforeEach
    public void setUp() {
        Calendar c1 = Calendar.getInstance();
        c1.set(2023, Calendar.MAY, 2, 18, 30);
        event1 = new Event("Concierto municipal de Móstoles", "Concierto ofrecido por ...",  c1.getTime(), 19.99, 50);
        event2 = new Event("Concierto municipal de Fuenlabrada", "Concierto ofrecido por ...",  c1.getTime(), 29.99, 70);

        organizer = new User("URJC", "patxi@example.com", "pass", "Universidad Rey Juan Carlos");
        client = new User("Carlos","calos@gmail.es","pass");
    }

    @Test
    @DisplayName("Check that all events of an organizer can be fetched")
    @WithMockUser(username = "Carlos", password = "pass", roles = "CLIENT")
    public void getEventsFromOrgTest() throws Exception {
        // Configurar el comportamiento del mock de UserService
        when(userService.findByUsername("URJC")).thenReturn(Optional.of(organizer));

        // Configurar el comportamiento del mock de EventService
        List<Event> fakeEvents = Arrays.asList(event1, event2);
        when(eventService.findByUser(organizer)).thenReturn(fakeEvents);

        // Realizar la solicitud GET y verificar la respuesta
        mvc.perform(
                get("/api/events")
                        .param("org", "URJC")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        // Verificar que se llamó al método correspondiente en EventService
        verify(eventService).findByUser(organizer);
    }

    @Test
    @DisplayName("Get an event by ID")
    @WithMockUser(username = "Carlos", password = "pass", roles = "CLIENT")
    public void getEventByIdTest() throws Exception {
        event1.setId(1L);

        when(eventService.findById(1L)).thenReturn(Optional.of(event1));

        mvc.perform(
                get("/api/events/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        verify(eventService).findById(event1.getId());
    }

    @Test
    @DisplayName("Get My Events")
    @WithMockUser(username = "patxi@example.com", password = "pass", roles = "ORGANIZATION")
    public void getMyEvents() throws Exception{
        when(userService.findByEmail(organizer.getEmail())).thenReturn(Optional.of(organizer));

        // Configurar el comportamiento del mock de EventService
        List<Event> fakeEvents = Arrays.asList(event1, event2);
        when(eventService.findByUser(organizer)).thenReturn(fakeEvents);

        // Realizar la solicitud GET y verificar la respuesta
        mvc.perform(
                get("/api/organizers/events")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        // Verificar que se llamó al método correspondiente en EventService
        verify(eventService).findByUser(organizer);
    }


    @Test
    @DisplayName("Delete a event")
    @WithMockUser(username = "patxi@example.com", password = "pass", roles = "ORGANIZATION")
    public void deleteEvent() throws Exception {
        event1.setId(1L);
        event1.setOrganization(organizer);
        when(userService.findByEmail(organizer.getEmail())).thenReturn(Optional.of(organizer));
        when(eventService.findById(1L)).thenReturn(Optional.of(event1));

        mvc.perform(
                delete("/api/organizers/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());

        // Verifica que el método de servicio correspondiente haya sido llamado
        verify(eventService).deleteEvent(event1);
    }
}
