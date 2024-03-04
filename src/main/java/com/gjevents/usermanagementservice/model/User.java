package com.gjevents.usermanagementservice.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Entity;

@Entity
public class User extends  UserBase{

        protected String phoneNumber;

        protected String address;


        protected String accountState;


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