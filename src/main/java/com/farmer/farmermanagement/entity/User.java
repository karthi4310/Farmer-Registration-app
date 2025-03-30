package com.farmer.farmermanagement.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(unique = true, nullable = false)
	private String phoneNumber;

	@Column(nullable = false)
	private String password; // Ensure this is hashed when storing in DB

	@Column(nullable = true)
	private LocalDate dateOfBirth; // ✅ Change String to LocalDate

	@Column(nullable = true)
	private String gender;

	@Column(nullable = true)
	private String country;

	@Column(nullable = true)
	private String state;

	@Column(nullable = true)
	private String pinCode;

	@Column(nullable = true)
	private String timeZone;
}
