package com.example.finance_tracker;

import com.example.finance_tracker.model.EmployerDetails;
import com.example.finance_tracker.model.Role;
import com.example.finance_tracker.model.User;
import com.example.finance_tracker.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {

		return args -> {
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_USER"));

			userService.saveUser(new User(
					null,
					"Sean",
					"de Sheep",
					"sean@gmail.com",
					"seanthesheep",
					LocalDate.of(1998, 04, 30),
					"aaa",
					false,
					new ArrayList<>(),
//					new ArrayList<>(),
					null));

			userService.assignRoleToUser("seanthesheep", "ROLE_ADMIN");
		};
	}
}
