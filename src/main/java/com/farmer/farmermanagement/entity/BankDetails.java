package com.farmer.farmermanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Account number is required")
	@Column(nullable = false, unique = true)
	private String accountNumber;

	@NotBlank(message = "IFSC code is required")
	private String ifscCode;

	@NotBlank(message = "Bank name is required")
	private String bankName;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "farmer_id", nullable = false, unique = true)
	private Farmer farmer;
}
