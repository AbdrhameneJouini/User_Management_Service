package com.gjevents.usermanagementservice.model;

import jakarta.persistence.Entity;
import java.util.Date;

@Entity
public class User extends UserBase {
    private Date deactivationDate;
    protected String phoneNumber;
    protected String address;
    protected String accountState;
    private boolean emailVerified;

    public User() {
    }

    public User(String login, String password, String email, String firstName, String lastName, String phoneNumber, String address) {
        super(login, password, email, firstName, lastName);
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public String getAccountState() {
        return accountState;
    }

    public void setAccountState(String accountState) {
        this.accountState = accountState;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDeactivationDate() {
        return deactivationDate;
    }

    public void setDeactivationDate(Date deactivationDate) {
        this.deactivationDate = deactivationDate;
    }

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
                ", role='User'}";
    }
}