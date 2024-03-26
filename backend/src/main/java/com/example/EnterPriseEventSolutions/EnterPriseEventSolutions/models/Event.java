package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
public class Event {
    public interface BasicInfo{}
    public interface OrgInfo extends BasicInfo {}
    public interface ClientInfo extends BasicInfo {}


    public Event() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(BasicInfo.class)
    private Long id;
    @JsonView(BasicInfo.class)
    private String name;
    @JsonView(BasicInfo.class)
    private String description;
    @JsonView(BasicInfo.class)
    private int max_capacity;
    @JsonView(BasicInfo.class)
    private int current_capacity;
    @JsonView(BasicInfo.class)
    private double price;

    private LocalDateTime creationTime;

    @DateTimeFormat
    private Date updateDateTime;


    @JsonSerialize(using = UserSerializer.class)
    @ManyToOne
    private User organization;

    @JsonBackReference
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Ticket> tickets;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMax_capacity() {
        return max_capacity;
    }

    public void setMax_capacity(int max_capacity) {
        this.max_capacity = max_capacity;
    }

    public int getCurrent_capacity() {
        return current_capacity;
    }

    public void setCurrent_capacity(int current_capacity) {
        this.current_capacity = current_capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Date updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public User getOrganization() {
        return organization;
    }

    public void setOrganization(User organization) {
        this.organization = organization;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }


    public Event(String name, String description, Date date, Double price, int max_capacity) {
        this.name = name;
        this.description = description;
        this.updateDateTime = date;
        this.price = price;
        this.max_capacity = max_capacity;
        this.current_capacity = 0;
        this.creationTime = LocalDateTime.now();
    }
}
