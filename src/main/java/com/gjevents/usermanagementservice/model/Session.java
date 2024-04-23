package com.gjevents.usermanagementservice.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;



@Entity
public class Session {


    @Id
    @GeneratedValue
    @Column(name = "PRIMARY_ID")
    private Long id;



    @NotNull
    @Column(name = "SESSION_ID")
    private String sessionId;


    @NotNull
    @Column(name = "CREATION_TIME")
    private Date creationTime;

    @NotNull
    @Column(name = "EXPIRY_TIME")
    private Date expiryTime;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;



    public Session() {
    }

    public Session(Long primaryId, String sessionId, Date creationTime, Date expiryTime, User user) {
        this.id = primaryId;
        this.sessionId = sessionId;
        this.creationTime = creationTime;
        this.expiryTime = expiryTime;
        this.user = user;
    }



    public Long getPrimaryId() {
        return id;
    }

    public void setPrimaryId(Long primaryId) {
        this.id = primaryId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
