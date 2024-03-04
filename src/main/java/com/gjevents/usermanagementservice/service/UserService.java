package com.gjevents.usermanagementservice.service;

import com.gjevents.usermanagementservice.controller.UserLoginResponse;
import com.gjevents.usermanagementservice.model.Administrator;
import com.gjevents.usermanagementservice.model.User;
import com.gjevents.usermanagementservice.repository.AdminRepository;
import com.gjevents.usermanagementservice.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean login(String login, String password) {
        User user = userRepository.findByEmail(login);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        } else {
            Administrator admin = adminRepository.findAdministratorByEmail(login);
            if (admin != null) {
                return passwordEncoder.matches(password, admin.getPassword());
            }
        }
        return false;
    }





    public User getUserData(String login) {
        return userRepository.findByEmail(login);
    }

    public UserLoginResponse getUserLoginResponse(String login, String password) {
        boolean isSuccessful = login(login, password);
        if (isSuccessful) {
            User user = getUserData(login);


            UserLoginResponse userResponse = new UserLoginResponse();

            if (user != null) {
                userResponse.setTempUserId(user.getId());
                userResponse.setUserData(user.toString());
            } else {
                Administrator admin = adminRepository.findAdministratorByEmail(login);
                if (admin != null) {
                    userResponse.setTempUserId(admin.getId());
                    userResponse.setUserData(admin.toString());
                } else {
                    return null;
                }
            }
            return userResponse;
        } else {
            return null;
        }
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


}