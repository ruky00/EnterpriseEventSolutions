package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private long tokenid;

    @Column(name="confirmation_token")
    private String confirmationToken;


    private LocalDateTime createdDate;
    private LocalDateTime expireAt;
    private LocalDateTime comfirmTime;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    public long getTokenid() {
        return tokenid;
    }

    public User getUser() {
        return user;
    }

    public void setClient(User client) {
        this.user = client;
    }

    public void setTokenid(long tokenid) {
        this.tokenid = tokenid;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(LocalDateTime expireAt) {
        this.expireAt = expireAt;
    }

    public LocalDateTime getComfirmTime() {
        return comfirmTime;
    }

    public void setComfirmTime(LocalDateTime comfirmTime) {
        this.comfirmTime = comfirmTime;
    }
    public ConfirmationToken(){
    }

    public ConfirmationToken(String token,LocalDateTime createdDate,LocalDateTime expireAt, User user){
        this.confirmationToken = token;
        this.createdDate = createdDate;
        this.expireAt = expireAt;
        this.user = user;
    }
}