package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.services;

import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.Event;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.UserTipeEnum;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void enableUser(User user) {
        user.setEnable(true);
        userRepository.save(user);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    //Todas por tipo
    public List<User> findAllByRole(UserTipeEnum type){
        return userRepository.findAllByRole(type);
    }


    //Admin services
    public Map<String, Integer> getUsersCountByMonth(List<User> userList) {
        Map<String, Integer> userCountByMonth = new HashMap<>();

        for (User user : userList) {
            LocalDateTime registrationDate = user.getCreateDateTime();
            Month month = registrationDate.getMonth();
            String monthName = month.toString();

            // Verificar si el mes ya está en el mapa
            if (userCountByMonth.containsKey(monthName)) {
                // Si el mes ya está en el mapa, aumentar el conteo de usuarios para ese mes
                userCountByMonth.put(monthName, userCountByMonth.get(monthName) + 1);
            } else {
                // Si el mes no está en el mapa, agregarlo con un conteo inicial de 1
                userCountByMonth.put(monthName, 1);
            }
        }
        return userCountByMonth;
    }

    public Map<String, Integer> getEventsCountByMonth(List<Event> eventList) {
        Map<String, Integer> EventCountByMonth = new HashMap<>();

        for (Event event : eventList) {
            LocalDateTime registrationDate = event.getCreationTime();
            Month month = registrationDate.getMonth();
            String monthName = month.toString();

            // Verificar si el mes ya está en el mapa
            if (EventCountByMonth.containsKey(monthName)) {
                // Si el mes ya está en el mapa, aumentar el conteo de eventos para ese mes
                EventCountByMonth.put(monthName, EventCountByMonth.get(monthName) + 1);
            } else {
                // Si el mes no está en el mapa, agregarlo con un conteo inicial de 1
                EventCountByMonth.put(monthName, 1);
            }
        }
        return EventCountByMonth;
    }




}






