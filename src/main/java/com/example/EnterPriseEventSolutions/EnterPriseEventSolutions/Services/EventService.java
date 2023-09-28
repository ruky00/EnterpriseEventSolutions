package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Services;

import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.Event;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.Ticket;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.User;
import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    public List<Event> findByUser(User user){return  eventRepository.findByUser(user);}


    public void saveEvent(Event event){
        event.setCurrent_capacity(0);
        eventRepository.save(event);
    }

    public Event updateEvent(Event event){
        Event oldEvent = eventExists(event.getId());
        if(event.getMax_capacity() < oldEvent.getMax_capacity()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't reduce max capacity of the event");
        }

        oldEvent.setName(event.getName());
        oldEvent.setDescription(event.getDescription());
        oldEvent.setUpdateDateTime(event.getUpdateDateTime());
        oldEvent.setPrice(event.getPrice());

        return eventRepository.save(event);

    }

    @Transactional
    public Event checkIfFull(long id){
        Event event = eventExists(id);

        int i = eventRepository.incrementCurrentCapacity(id);
        if(i==1){
            return event;
        }else{
            throw new ResponseStatusException(HttpStatus.CONFLICT,"This event is full");
        }
    }


    public void deleteEvent(Event event){
        eventRepository.delete(event);
    }


    private Event eventExists(long eventId){
        Optional<Event> opEvent = eventRepository.findById(eventId);
        if (opEvent.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event not longer exist");
        }
        return opEvent.get();
    }
}
