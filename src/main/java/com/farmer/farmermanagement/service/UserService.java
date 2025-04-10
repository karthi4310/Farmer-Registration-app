package com.farmer.farmermanagement.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.farmer.farmermanagement.dto.UserDTO;
import com.farmer.farmermanagement.entity.User;
import com.farmer.farmermanagement.exception.UserNotFoundException;
import com.farmer.farmermanagement.mapper.UserMapper;
import com.farmer.farmermanagement.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

	private final UserRepository userRepository;
	private final OtpService otpService; // Using Firebase OTP service
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;

	public User registerUser(UserDTO userDTO) {
		log.info("Registering user with email: {}", userDTO.getEmail());
		User user = userMapper.toEntity(userDTO);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		User savedUser = userRepository.save(user);
		log.info("User registered successfully with ID: {}", savedUser.getId());
		return savedUser;
	}

	// ✅ Forgot Password - Sends OTP
	public String forgotPassword(String emailOrPhone) {
		log.info("Processing forgot password for: {}", emailOrPhone);

		Optional<User> user = userRepository.findByEmailOrPhoneNumber(emailOrPhone, emailOrPhone);
		if (user.isEmpty()) {
			log.warn("User not found for forgot password request: {}", emailOrPhone);
			throw new UserNotFoundException("User not found with given email or phone number.");
		}

		// Send OTP using Firebase
		String otpResponse = otpService.generateAndSendOtp(emailOrPhone); // Firebase OTP method
		log.info("OTP sent for forgot password: {}", otpResponse);
		return otpResponse;
	}

	// ✅ Forgot User ID - Sends OTP
	public String forgotUserId(String emailOrPhone) {
		log.info("Processing forgot user ID request for: {}", emailOrPhone);

		Optional<User> user = userRepository.findByEmailOrPhoneNumber(emailOrPhone, emailOrPhone);
		if (user.isEmpty()) {
			log.warn("User not found for forgot user ID request: {}", emailOrPhone);
			throw new UserNotFoundException("User not found.");
		}

		// Send OTP using Firebase
		String otpResponse = otpService.generateAndSendOtp(emailOrPhone); // Firebase OTP method
		log.info("OTP sent for forgot user ID: {}", otpResponse);
		return otpResponse;
	}

	// ✅ Verify OTP and Reset Password
	public boolean verifyOtpAndResetPassword(String idToken, String emailOrPhone, String newPassword) {
		log.info("Verifying OTP for password reset.");

		// Verify OTP using Firebase token
		if (otpService.verifyOtp(idToken)) { // Firebase OTP verification
			User user = userRepository.findByEmailOrPhoneNumber(emailOrPhone, emailOrPhone).orElseThrow(() -> {
				log.warn("User not found for OTP verification: {}", emailOrPhone);
				return new UserNotFoundException("User not found.");
			});

			user.setPassword(passwordEncoder.encode(newPassword));
			userRepository.save(user);

			log.info("Password reset successful for user: {}", emailOrPhone);
			return true;
		}
		log.warn("Invalid or expired OTP.");
		return false;
	}

//    // ✅ Extract user email or phone from Firebase token
//    private String extractUserIdentifierFromToken(String idToken) {
//        return otpService.getUserEmailOrPhoneFromToken(idToken);  // Extract from Firebase token
//    }
}
