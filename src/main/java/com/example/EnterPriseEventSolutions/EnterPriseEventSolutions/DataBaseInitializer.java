package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions;


import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.Event;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.UserTipeEnum;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Repositories.EventRepository;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;

@Component
public class DataBaseInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataBaseInitializer.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {

        if (!this.userRepository.findById(1L).isPresent()) {
            log.info("--> EMPTY DATABASE, CREATING DATA");

            // CREATE ORGANIZER
            User u2 = new User("Patxi", "jose.juan@urjc.es", passwordEncoder.encode("pass"), "URJC eventos ETSI");
            u2.setEnable(true);
            userRepository.save(u2);

            // CREATE CUSTOMER
            User u3 = new User("Michel", "carlos.fernandez@urjc.es", passwordEncoder.encode("pass"));
            u3.setEnable(true);
            userRepository.save(u3);

            // CREATE AN EVENT
            Calendar c1 = Calendar.getInstance();
            c1.set(2021, Calendar.MAY, 2, 18, 30);
            Event e1 = new Event("Seminario Ciberseguridad 2023", "Concierto ofrecido por la ETSI", c1.getTime(), 19.99, 50);
            e1.setOrganization(u2);
            eventRepository.save(e1);

        } else {
            log.info("--> DATABASE WITH DATA");
        }

    }
}
