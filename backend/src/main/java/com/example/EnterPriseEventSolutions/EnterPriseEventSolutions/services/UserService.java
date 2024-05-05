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
        return userRepository.findByUsername(username);
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
    public int[] getUsersCountByMonth(List<User> userList) {
        int[] userCountByMonth = new int[12]; // Initialize array with 12 positions

        for (User user : userList) {
            LocalDateTime registrationDate = user.getCreateDateTime();
            int monthIndex = registrationDate.getMonthValue() - 1; // Convert month to index (0-11)

            // Increment count for the corresponding month
            userCountByMonth[monthIndex]++;
        }

        return userCountByMonth;
    }

    public int[] getEventsCountByMonth(List<Event> eventList) {
        int[] eventCountByMonth = new int[12];

        for (Event event : eventList) {
            LocalDateTime registrationDate = event.getCreationTime();
            int monthIndex = registrationDate.getMonthValue() - 1; // Convert month to index (0-11)

            // Increment count for the corresponding month
            eventCountByMonth[monthIndex]++;
        }

        return eventCountByMonth;
    }
    }








