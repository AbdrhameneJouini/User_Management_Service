package com.gjevents.usermanagementservice.service;

import com.gjevents.usermanagementservice.controller.PasswordResetRequest;
import com.gjevents.usermanagementservice.controller.UserLoginResponse;
import com.gjevents.usermanagementservice.controller.UserSignupRequest;
import com.gjevents.usermanagementservice.model.*;
import com.gjevents.usermanagementservice.repository.AdminRepository;
import com.gjevents.usermanagementservice.repository.OrganizerRepository;
import com.gjevents.usermanagementservice.repository.TokenRepository;
import com.gjevents.usermanagementservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {


    private final UserRepository userRepository;


    private final AdminRepository adminRepository;
    private final OrganizerRepository organizerRepository;

    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;


    @Autowired
    public UserService(UserRepository userRepository,MailSenderService mailSenderService,OrganizerRepository organizerRepository ,AdminRepository adminRepository, PasswordEncoder passwordEncoder, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSenderService = mailSenderService;
        this.tokenRepository = tokenRepository;
        this.organizerRepository = organizerRepository;
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
        System.out.println("login: " + login + " password: " + password);
        boolean isSuccessful = login(login, password);
        if (isSuccessful) {
            User user = getUserData(login);


            UserLoginResponse userResponse = new UserLoginResponse();

            if (user != null) {
                userResponse.setTempUserId(user.getId());
                userResponse.setUserData(user.toString());
                userResponse.setUser(user);
            } else {
                Administrator admin = adminRepository.findAdministratorByEmail(login);
                if (admin != null) {
                    userResponse.setTempUserId(admin.getId());
                    userResponse.setUserData(admin.toString());
                    userResponse.setUser(null);
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

    public boolean forgotPassword(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            // Check if a token of type 'password_reset' already exists for the user
            Token existingToken = tokenRepository.findByUserAndTokenType(user, "password_reset");
            if (existingToken != null) {
                // If it does, delete it
                tokenRepository.delete(existingToken);
            }

            // Generate a new token
            String token = UUID.randomUUID().toString();
            // Save the new token in the database
            savePasswordResetToken(user, token);
            // Send the email
            sendPasswordResetEmail(user, token);
            return true;
        } else {
            return false;
        }
    }
    private void savePasswordResetToken(User user, String tokenValue) {
        Token token = new Token();
        token.setValue(tokenValue);
        token.setUser(user);
        token.setTokenType("password_reset");
        // expiry date is set to 24 hours from now
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        token.setExpiryDate(cal.getTime());

        tokenRepository.save(token);
    }

    private void sendPasswordResetEmail(User user, String token) {
        String subject = "Password Reset Request";
        String resetUrl = "http://localhost:3000/reset-password?token=" + token;
        String body = "To reset your password, click the following link: " + resetUrl;

        mailSenderService.sendNewMail(user.getEmail(), subject, body);
    }

    public boolean resetPassword(PasswordResetRequest passwordResetRequest) {
        Token token = tokenRepository.findByValue(passwordResetRequest.getToken());
        if (token != null && token.getExpiryDate().after(new Date())) {
            User user = token.getUser();
            user.setPassword(passwordEncoder.encode(passwordResetRequest.getNewPassword()));
            userRepository.save(user);
            // Delete the token after resetting the password
            tokenRepository.delete(token);
            return true;
        } else {
            return false;
        }
    }
    public void updateUser(User user) {
        // Find the existing user
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser != null) {

            existingUser.setLogin(user.getLogin());
            existingUser.setPassword(user.getPassword());
            existingUser.setEmail(user.getEmail());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setAddress(user.getAddress());
            existingUser.setAccountState(user.getAccountState());

            // Save the updated user back to the database
            userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("User with id " + user.getId() + " does not exist");
        }
    }

    public void updateOrganizer(Organizer organizer) {
        // Find the existing organizer
        Organizer existingOrganizer = organizerRepository.findById(organizer.getId()).orElse(null);
        if (existingOrganizer != null) {

            existingOrganizer.setLogin(organizer.getLogin());
            existingOrganizer.setPassword(organizer.getPassword());
            existingOrganizer.setEmail(organizer.getEmail());
            existingOrganizer.setFirstName(organizer.getFirstName());
            existingOrganizer.setLastName(organizer.getLastName());
            existingOrganizer.setPhoneNumber(organizer.getPhoneNumber());
            existingOrganizer.setAddress(organizer.getAddress());
            existingOrganizer.setAccountState(organizer.getAccountState());
            existingOrganizer.setLocation(organizer.getLocation());

            // Save the updated organizer back to the database
            organizerRepository.save(existingOrganizer);
        } else {
            throw new IllegalArgumentException("Organizer with id " + organizer.getId() + " does not exist");
        }
    }

    @Autowired
    private EmailService emailService;

    public boolean registerUser(String login, String password, String email, String firstName, String lastName, String phoneNumber, String address) {
        boolean userExists = userRepository.existsUserByEmail(email);
        if (!userExists) {
            String verificationToken = generateVerificationToken();
            User user = new User(login, password, email, firstName, lastName, phoneNumber, address);
            userRepository.save(user);
            saveVerificationToken(user, verificationToken); // Save verification token
            sendVerificationEmail(email, verificationToken); // Send verification email with token
            return true;
        }
        return false;
    }

    private void sendVerificationEmail(String email, String token) {
        String subject = "Email Verification";
        String verificationLink = "http://localhost:8080/verify?token=" + token; // Change the URL accordingly
        String body = "Please click the following link to verify your email address: " + verificationLink;
        emailService.sendEmail(email, subject, body);
    }

    private String generateVerificationToken() {
        // Generate a random verification token
        return UUID.randomUUID().toString();
    }

    private void saveVerificationToken(User user, String token) {
        Token verificationToken = new Token();
        verificationToken.setValue(token);
        verificationToken.setUser(user);
        verificationToken.setTokenType("email_verification");
        Date expiryDate = new Date(); // Créer une nouvelle date
        // Ajouter 30 minutes à la date actuelle
        expiryDate.setTime(expiryDate.getTime() + (30 * 60 * 1000));

        // Utiliser la méthode setExpiryDate pour définir la date d'expiration sur la date calculée
        verificationToken.setExpiryDate(expiryDate);

        tokenRepository.save(verificationToken);
    }


    public boolean verifyEmail(String token) {
        Token verificationToken = tokenRepository.findByValueAndTokenType(token, "email_verification");
        if (verificationToken != null && !verificationToken.isExpired()) {
            User user = verificationToken.getUser();
            user.setEmailVerified(true);
            userRepository.save(user);
            tokenRepository.delete(verificationToken);
            return true;
        }
        return false;
    }

    public void disableAccount(User user) {   // c'est la fonction qui va déasactiver le compte
        user.setAccountState("DISABLED"); // lehne AccountState dans la classe user va prendre la valeur disabled
        user.setDeactivationDate(new Date()); // Mettre à jour la date de désactivation
        userRepository.save(user);
    }

    public void enableAccount(User user) {
        user.setAccountState("ACTIVE");
        user.setDeactivationDate(null); // Réinitialiser la date de désactivation
        userRepository.save(user);
    }

    public boolean shouldDeleteAccount(User user) { //cette fonction pour vérifier q'un compte doit etre supprimer ou non
        Date expirationDate = calculateExpirationDate(user);
        return new Date().after(expirationDate);
    }

    private Date calculateExpirationDate(User user) { //si le compte était désactiver plus que 30jours
        Calendar cal = Calendar.getInstance();
        cal.setTime(user.getDeactivationDate());
        cal.add(Calendar.DAY_OF_MONTH, 30); // Ajoute 30 jours
        return cal.getTime();
    }

    public void processAccountDeletion(User user) { //la supprission du compte apres vérification
        if (shouldDeleteAccount(user)) {
            deleteAccount(user);
        }
    }

    public void deleteAccount(User user) {
        userRepository.delete(user);
    }


}