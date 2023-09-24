package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Services;

import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.Event;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Optional<Event> findById(long id){
        return eventRepository.findById(id);
    }

    public Optional<Event> findByName(String name){
        return eventRepository.findByName(name);
    }

    public List<Event> findAll(){
        return eventRepository.findAll();
    }

    public void saveUser(Event event){
        eventRepository.save(event);
    }

    public void deleteUser(Event event){
        eventRepository.delete(event);
    }



}
