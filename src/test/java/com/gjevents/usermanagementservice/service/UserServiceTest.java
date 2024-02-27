package com.gjevents.usermanagementservice.service;

import com.gjevents.usermanagementservice.TestConfig;
import com.gjevents.usermanagementservice.repository.UserRepository;
import com.gjevents.usermanagementservice.model.User;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Test
    public void testLogin() {
        String login = "Abdou48";
        String password = "58051050ABDOU";
        String encodedPassword = "$argon2id$v=19$m=65536,t=1,p=1$oWKq/U1fX/8GK3cex+3NvQ$WYapC5aab7GWuqr6do7TdMOh8VnHFc4ZlXSUjdJ8+Yo";

        User user = new User();
        user.setLogin(login);
        user.setPassword(encodedPassword);

        when(userRepository.findByLogin(login)).thenReturn(user);
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

        boolean result = userService.login(login, password);

        System.out.println("Expected user: " + user);
        System.out.println("Actual user: " + userRepository.findByLogin(login));
        System.out.println("Expected password match: true");
        System.out.println("Actual password match: " + passwordEncoder.matches(password, encodedPassword));
        System.out.println("Expected result: true");
        System.out.println("Actual result: " + result);

        assertTrue(result);
        verify(userRepository, times(1)).findByLogin(login);
        verify(passwordEncoder, times(1)).matches(password, encodedPassword);
    }
}