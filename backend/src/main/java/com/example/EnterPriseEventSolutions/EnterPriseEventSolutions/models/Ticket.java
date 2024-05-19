package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models;


import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Ticket {

    public interface BasicInfo{}
    public interface DetailedInfo{}

    public Ticket(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(BasicInfo.class)
    private Long id;

    @JsonView(BasicInfo.class)
    private double price;

    @JsonBackReference
    @ManyToOne
    @JsonView(DetailedInfo.class)
    private User client;


    @ManyToOne
    @JsonView(BasicInfo.class)
    private Event event;

    @Lob
    @JsonView(DetailedInfo.class)
    private byte[] qrCode;

    @CreationTimestamp
    private Date creationTime;

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public byte[] getQrCode() {
        return qrCode;
    }

    public void setQrCode(byte[] qrCode) {
        this.qrCode = qrCode;
    }

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
        this.qrCode= null;
    }

}
