package com.farmer.farmermanagement.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.farmer.farmermanagement.entity.User;
import com.farmer.farmermanagement.repository.UserRepository;

@Component
public class UserSeeder implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) {
		String email = "john.doe@example.com";

		// Check if the user already exists
		if (userRepository.findByEmail(email).isEmpty()) {
			User user = User.builder().firstName("John").lastName("Doe").email(email).phoneNumber("9876543210")
					.password(passwordEncoder.encode("Sudheer")) // Hash the password
					.gender("Male").country("India").state("Karnataka").pinCode("560001").timeZone("IST").build();

			userRepository.save(user);

		} else {
			System.out.println("⚠️ User already exists, skipping insertion.");
		}
	}
}
