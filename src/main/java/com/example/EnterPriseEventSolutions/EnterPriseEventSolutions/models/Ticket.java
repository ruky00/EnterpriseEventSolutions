package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models;


import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;

@Entity
public class Ticket {

    public Ticket(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double price;

    @JsonBackReference
    @ManyToOne
    private User client;


    @ManyToOne
    private Event event;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Ticket(User client, Event event){
        this.client=client;
        this.event=event;
    }

}
