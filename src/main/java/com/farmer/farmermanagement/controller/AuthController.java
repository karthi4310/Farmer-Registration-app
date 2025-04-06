package com.farmer.farmermanagement.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.farmer.farmermanagement.dto.LoginRequest;
import com.farmer.farmermanagement.dto.UserDTO;
import com.farmer.farmermanagement.dto.UserResponseDTO;
import com.farmer.farmermanagement.entity.User;
import com.farmer.farmermanagement.security.JwtUtil;
import com.farmer.farmermanagement.service.OtpService;
import com.farmer.farmermanagement.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final UserService userService;
	private final OtpService otpService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest request) {
		Authentication authentication = null;
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));

		} catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Login failed: " + e.getMessage());
		}

		String token = jwtUtil.generateToken(authentication);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);
		return ResponseEntity.ok().headers(headers).body("logged in successfully");
	}

	@PostMapping("/register")
	public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
		User user = userService.registerUser(userDTO);
		return ResponseEntity.ok(UserResponseDTO.fromEntity(user, "User registered successfully."));
	}

	@PostMapping("/forgot-user-id")
	public ResponseEntity<String> forgotUserId(@RequestParam String emailOrPhone) {
		try {
			return ResponseEntity.ok(userService.forgotUserId(emailOrPhone));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Failed to send user ID: " + e.getMessage());
		}
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestParam String emailOrPhone) {
		return ResponseEntity.ok(userService.forgotPassword(emailOrPhone));
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<String> verifyOtp(@RequestParam String idToken) {
		if (otpService.verifyOtp(idToken))
			return ResponseEntity.ok("OTP verified successfully.");

		return ResponseEntity.badRequest().body("Invalid or expired OTP.");

	}

	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestParam String emailOrPhone, @RequestParam String otp,
			@RequestParam String newPassword) {
		if (userService.verifyOtpAndResetPassword(emailOrPhone, otp, newPassword))
			return ResponseEntity.ok("Password reset successfully.");

		return ResponseEntity.badRequest().body("Invalid OTP or expired OTP.");

	}

	@GetMapping("/test")
	public String test() {
		return new String("this is a test endpoint");
	}
}
