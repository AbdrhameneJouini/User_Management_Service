package com.gjevents.usermanagementservice.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserSignupRequest {
    @NotBlank(message="login is mandatory")
    @Size(min=3, max=20 , message = "login must be between 3 and 20 characters")
    String login;
    @NotBlank(message="password is mandatory")
    @Size(min=8 , message = "login must be over 8 characters")
    String password;
    @NotBlank(message="email is required")
    String email;
    @NotBlank(message="firstName is required")
    String firstName;
    @NotBlank(message="lastName is required")
    String lastName;
    String phoneNumber;
    String address;

    String accountType ;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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


}