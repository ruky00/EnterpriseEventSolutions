package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.UnitaryTest;


import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Repositories.EventRepository;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Repositories.TicketRepository;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@TestPropertySource(locations = "classpath:application.properties")
public class DatabaseInitializationTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testDatabaseInitialization() throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            assertTrue(connection.isValid(2));
        }

        assertNotNull(ticketRepository, "Table created correctly");

        assertNotNull(eventRepository, "Table created correctly");

        assertNotNull(userRepository, "Table created correctly");
    }
}
