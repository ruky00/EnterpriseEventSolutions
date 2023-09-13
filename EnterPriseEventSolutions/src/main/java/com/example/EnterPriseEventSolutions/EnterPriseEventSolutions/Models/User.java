package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models;


import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class User {

    public interface BasicInfo{}
    public interface OrgInfo extends BasicInfo{}
    public interface ClientInfo extends  BasicInfo{}

    public User() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @JsonView(BasicInfo.class)
    private String username;


    @Column(unique = true)
    @JsonView(BasicInfo.class)
    private String email;

    @JsonView(BasicInfo.class)
    private long phone;

    @JsonView(BasicInfo.class)
    private String encodedPassword;

    @JsonView(BasicInfo.class)
    private UserTipeEnum role;


    @JsonView(OrgInfo.class)
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<Event> events;

    @JsonView(ClientInfo.class)
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Ticket> tickets;


    @CreationTimestamp
    private LocalDateTime createDateTime;

    private MultipartFile image;


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

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public User(String username, String email, String encodedPassword, UserTipeEnum role, LocalDateTime createDateTime){
        this.username=username;
        this.email=email;
        this.encodedPassword=encodedPassword;
        this.role=role;
        this.createDateTime=createDateTime;
    }


}
