package com.gjevents.usermanagementservice.controller;

import com.gjevents.usermanagementservice.model.Administrator;
import com.gjevents.usermanagementservice.model.Organizer;
import com.gjevents.usermanagementservice.model.Session;
import com.gjevents.usermanagementservice.model.User;
import com.gjevents.usermanagementservice.service.SessionService;
import com.gjevents.usermanagementservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.security.crypto.bcrypt.BCrypt;

import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final SessionService sessionService;


    @Autowired
    public UserController(UserService userService, SessionService sessionService){

        this.userService = userService;
        this.sessionService = sessionService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        boolean result = userService.forgotPassword(request.getEmail());
        if (result) {
            return ResponseEntity.ok("Password reset email sent.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email does not exist.");
        }
    }
    @PostMapping("/update-user")
    public ResponseEntity<String> modifyAccount(@Valid @RequestBody User user) {
        try {
            userService.updateUser(user);
            return ResponseEntity.ok("Account has been modified.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/update-organizer")
    public ResponseEntity<String> modifyAccount(@Valid @RequestBody Organizer organizer) {
        try {
            userService.updateOrganizer(organizer);
            return ResponseEntity.ok("Account has been modified.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }




    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody PasswordResetRequest passwordResetRequest) {
        boolean result = userService.resetPassword(passwordResetRequest);
        if (result) {
            return ResponseEntity.ok("Password has been reset.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token.");
        }
    }
    @GetMapping("/is-logged-in")
    public ResponseEntity<String> isLoggedIn(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    System.out.println("User is Logged in");
                        return ResponseEntity.ok("User is logged in");
                }
            }
        }
        System.out.println("User is not Logged in");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User is not logged in");
    }





    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Login request received");
        UserLoginResponse userResponse = userService.getUserLoginResponse(userLoginRequest.getLogin(), userLoginRequest.getPassword());

        if (userResponse != null) {
            System.out.println("User found");


            // Generate a strong random session ID
            String sessionId = UUID.randomUUID().toString();

            // Create a new Session object
            Session newSession = new Session();
            newSession.setSessionId(sessionId);
            newSession.setCreationTime(new Date());
            newSession.setExpiryTime(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)); // 1 day later
            newSession.setUser(userResponse.getUser());


            // Save the Session object to the database
            // Assuming you have a SessionService with a saveSession method
            sessionService.saveSession(newSession);




            Cookie sessionCookie = new Cookie("JSESSIONID", sessionId );
            userResponse.setTempUserId(null);
            sessionCookie.setMaxAge(24 * 60 * 60); // 1 day
            sessionCookie.setHttpOnly(true);
            sessionCookie.setSecure(true);
            sessionCookie.setPath("/");
            response.addCookie(sessionCookie);

            userResponse.setResponseState("success");
            return ResponseEntity.ok(userResponse);
        } else {
            System.out.println("User not found");
            userResponse = new UserLoginResponse();
            userResponse.setResponseState("Incorrect Login or Password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userResponse);

        }
    }







    @GetMapping("/user-data")
    public ResponseEntity<Map<String, Object>> getUserData(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    System.out.println("JSESSIONID cookie value: " + cookie.getValue());

                    // Retrieve the Session object from the database using the session ID from the cookie
                    Session session = sessionService.getSessionBySessionId(cookie.getValue());

                    if (session != null && session.getExpiryTime().after(new Date())) {
                        System.out.println("Session ID when retrieved: " + session.getSessionId());
                        User user = session.getUser();
                        if (user != null) {
                            Map<String, Object> response = new HashMap<>();
                            response.put("user", user);
                            System.out.println("User found : " + user.getLogin());
                            if (user instanceof Organizer) {
                                response.put("role", "Organizer");
                            } else {
                                response.put("role", "User");
                            }
                            return ResponseEntity.ok(response);
                        } else {
                            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                        }
                    } else {
                        System.out.println("Session is null or expired");
                    }
                }
            }
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    System.out.println("JSESSIONID cookie value: " + cookie.getValue());

                    // Retrieve the Session object from the database using the session ID from the cookie
                    Session session = sessionService.getSessionBySessionId(cookie.getValue());

                    if (session != null) {
                        // Delete the Session object from the database
                        sessionService.deleteSession(session);

                        // Invalidate the JSESSIONID cookie
                        Cookie sessionCookie = new Cookie("JSESSIONID", null);
                        sessionCookie.setMaxAge(0);
                        sessionCookie.setHttpOnly(true);
                        sessionCookie.setSecure(true);
                        sessionCookie.setPath("/");
                        response.addCookie(sessionCookie);
                        System.out.println("Logged out");
                        return ResponseEntity.ok("Logged out");
                    } else {
                        System.out.println("Session is null");
                    }
                }
            }
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User is not logged in");
    }
}