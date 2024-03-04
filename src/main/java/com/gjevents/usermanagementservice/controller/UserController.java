package com.gjevents.usermanagementservice.controller;

import com.gjevents.usermanagementservice.model.User;
import com.gjevents.usermanagementservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;


import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/is-logged-in")
    public ResponseEntity<String> isLoggedIn(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {

                    return ResponseEntity.ok("User is logged in");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User is not logged in");
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request, HttpServletResponse response) {
        UserLoginResponse userResponse = userService.getUserLoginResponse(userLoginRequest.getLogin(), userLoginRequest.getPassword());
        if (userResponse != null) {

            HttpSession session = request.getSession(true);
            System.out.println("Session ID when created: " + session.getId());
            session.setAttribute("userId", userResponse.getTempUserId());
            Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());

            userResponse.setTempUserId(null);
            sessionCookie.setMaxAge(24 * 60 * 60); // 1 day
            sessionCookie.setHttpOnly(true);
            sessionCookie.setSecure(true); // Set this to true if your site is served over HTTPS
            sessionCookie.setPath("/");
            response.addCookie(sessionCookie);

            userResponse.setResponseState("success");
            return ResponseEntity.ok(userResponse);
        } else {
            userResponse = new UserLoginResponse();
            userResponse.setResponseState("Incorrect Login or Password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userResponse);
        }
    }

    @GetMapping("/user-data")
    public ResponseEntity<User> getUserData(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    System.out.println("JSESSIONID cookie value: " + cookie.getValue());
                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        System.out.println("Session ID when retrieved: " + session.getId());
                        Long userId = (Long) session.getAttribute("userId");
                        if (userId != null) {
                            User user = userService.getUserById(userId);
                            if (user != null) {
                                return ResponseEntity.ok(user);
                            } else {
                                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                            }
                        }
                    } else {
                        System.out.println("Session is null");
                    }
                }
            }
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        Cookie sessionCookie = new Cookie("JSESSIONID", null);
        sessionCookie.setMaxAge(0);
        sessionCookie.setHttpOnly(true);
        sessionCookie.setSecure(true);
        sessionCookie.setPath("/");
        response.addCookie(sessionCookie);
        return ResponseEntity.ok("Logged out");
    }
}