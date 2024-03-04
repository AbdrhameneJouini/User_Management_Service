package com.gjevents.usermanagementservice.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Entity;

@Entity
public class Organizer extends User{

    private String location;



    @Override
    public String toString() {
        return "{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", accountState='" + accountState + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='Organizer'}";
    }

}
