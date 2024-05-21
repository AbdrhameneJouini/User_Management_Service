package com.gjevents.usermanagementservice.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Entity;

@Entity
public class Organizer extends User{

    private String location;

    public Organizer(String login, String encode, String email, String firstName, String lastName, String phoneNumber, String address) {
        super(login, encode, email, firstName, lastName, phoneNumber, address);
    }

    public Organizer() {

    }

    public String getLocation() {
        return location;
    }



    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "{" +
                "  phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", accountState='" + accountState + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='Organizer'}";
    }

}
