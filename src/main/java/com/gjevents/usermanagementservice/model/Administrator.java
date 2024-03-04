package com.gjevents.usermanagementservice.model;


import jakarta.persistence.Entity;

@Entity
public class Administrator extends UserBase{



    @Override
    public String toString() {
        return "{ firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ",  login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", role='Administrator'}";
    }
}
