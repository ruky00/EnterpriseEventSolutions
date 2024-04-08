package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "_user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    public interface BasicInfo{}
    public interface OrgInfo extends BasicInfo{}
    public interface ClientInfo extends  BasicInfo{}
    public interface PrivateInfo extends  BasicInfo{}

    public User() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(BasicInfo.class)
    private Long id;

    @Column(unique = true)
    @JsonView(BasicInfo.class)
    private String username;


    @Column(unique = true)
    @JsonView(BasicInfo.class)
    private String email;


    @JsonView(PrivateInfo.class)
    private String encodedPassword;

    @JsonView(BasicInfo.class)
    private UserTipeEnum role;


    @JsonView(OrgInfo.class)
    @Column(length = 1000)
    private String description;

    @JsonView(OrgInfo.class)
    private String logo;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonView(OrgInfo.class)
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<Event> events;

    @JsonManagedReference
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    @JsonIgnore
    private boolean isEnable;

    @JsonView(BasicInfo.class)
    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }


    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createDateTime;

    @JsonView(BasicInfo.class)
    private String image;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public UserTipeEnum getRole() {
        return role;
    }

    public void setRole(UserTipeEnum role) {
        this.role = role;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public User(String username, String email, String encodedPassword){
        this.username=username;
        this.email=email;
        this.encodedPassword=encodedPassword;
        this.role=UserTipeEnum.CLIENT;

    }
    public User(String username, String email, String encodedPassword,String description){
        this.username=username;
        this.email=email;
        this.encodedPassword=encodedPassword;
        this.description = description;
        this.role=UserTipeEnum.ORGANIZATION;

    }

    @Override
    public String toString(){
        return "User [, name=" + this.getUsername() + ", email=" + this.getEmail() + ", encodedPassword="+ this.getEncodedPassword()+"]";
    }



}
