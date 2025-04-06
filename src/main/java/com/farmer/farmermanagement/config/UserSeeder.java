//package com.farmer.farmermanagement.config;
//
//import java.time.LocalDate;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import com.farmer.farmermanagement.entity.User;
//import com.farmer.farmermanagement.repository.UserRepository;
//
//@Component
//public class UserSeeder implements CommandLineRunner {
//
//	private final UserRepository userRepository;
//	private final PasswordEncoder passwordEncoder;
//
//	public UserSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//		this.userRepository = userRepository;
//		this.passwordEncoder = passwordEncoder;
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		// Define a single user
//		User user = User.builder().firstName("John").lastName("Doe").email("john.doe@example.com")
//				.phoneNumber("1234567890").password(passwordEncoder.encode("plainPassword123")) // Encode the password
//				.dateOfBirth(LocalDate.of(1990, 1, 15)).gender("Male").country("USA").state("California")
//				.pinCode("90001").timeZone("PST").build();
//
//		// Save to the database
//		userRepository.save(user);
//		System.out.println("Single user seeding completed with encoded password!");
//	}
//}
