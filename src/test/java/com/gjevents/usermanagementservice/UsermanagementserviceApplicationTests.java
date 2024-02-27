package com.gjevents.usermanagementservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

@SpringBootTest
class UsermanagementserviceApplicationTests {

	@BeforeAll
	static void setUp() {
		Dotenv dotenv = Dotenv.load();


		System.out.println("DB_URL: " + dotenv.get("DB_URL"));
		// Set the environment variables as system properties
		System.setProperty("DB_URL", Objects.requireNonNull(dotenv.get("DB_URL")));
		System.setProperty("DB_USERNAME", Objects.requireNonNull(dotenv.get("DB_USERNAME")));
		System.setProperty("DB_PASSWORD", Objects.requireNonNull(dotenv.get("DB_PASSWORD")));
		System.setProperty("MAIL_USERNAME", Objects.requireNonNull(dotenv.get("MAIL_USERNAME")));
		System.setProperty("MAIL_PASSWORD", Objects.requireNonNull(dotenv.get("MAIL_PASSWORD")));
	}

	@Test
	void contextLoads() {
	}

}