package com.gjevents.usermanagementservice;

import com.gjevents.usermanagementservice.service.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestConfig {

    @BeforeAll
    void setUp() {
        Dotenv dotenv = Dotenv.load();

        System.setProperty("DB_URL", Objects.requireNonNull(dotenv.get("DB_URL")));
        System.setProperty("DB_USERNAME", Objects.requireNonNull(dotenv.get("DB_USERNAME")));
        System.setProperty("DB_PASSWORD", Objects.requireNonNull(dotenv.get("DB_PASSWORD")));
        System.setProperty("MAIL_USERNAME", Objects.requireNonNull(dotenv.get("MAIL_USERNAME")));
        System.setProperty("MAIL_PASSWORD", Objects.requireNonNull(dotenv.get("MAIL_PASSWORD")));
    }


    @Bean
    public UserService userService() {
        return Mockito.mock(UserService.class);
    }
}