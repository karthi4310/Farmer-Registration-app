package com.farmer.farmermanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.farmer.farmermanagement.dto.UserDTO;
import com.farmer.farmermanagement.dto.UserResponseDTO;
import com.farmer.farmermanagement.entity.User;
import com.farmer.farmermanagement.service.OtpService;
import com.farmer.farmermanagement.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserService userService;
	private final OtpService otpService;

	@PostMapping("/register")
	public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
		User user = userService.registerUser(userDTO);
		return ResponseEntity.ok(UserResponseDTO.fromEntity(user, "User registered successfully."));
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestParam String emailOrPhone) {
		return ResponseEntity.ok(userService.forgotPassword(emailOrPhone));
	}

	@PostMapping("/forgot-user-id")
	public ResponseEntity<String> forgotUserId(@RequestParam String emailOrPhone) {
		return ResponseEntity.ok(userService.forgotUserId(emailOrPhone));
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<String> verifyOtp(@RequestParam String idToken) {
		if (otpService.verifyOtp(idToken)) {
			return ResponseEntity.ok("OTP verified successfully.");
		} else {
			return ResponseEntity.badRequest().body("Invalid or expired OTP.");
		}
	}

	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestParam String emailOrPhone, @RequestParam String otp,
			@RequestParam String newPassword) {
		if (userService.verifyOtpAndResetPassword(emailOrPhone, otp, newPassword)) {
			return ResponseEntity.ok("Password reset successfully.");
		} else {
			return ResponseEntity.badRequest().body("Invalid OTP or expired OTP.");
		}
	}

	@GetMapping("/test")
	public String test() {
		return "this is a test endpoint";
	}
}
