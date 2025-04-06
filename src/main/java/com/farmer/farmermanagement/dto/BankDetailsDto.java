package com.farmer.farmermanagement.dto;

import com.farmer.farmermanagement.enums.BankName;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankDetailsDto {

	@NotNull(message = "Bank name is required")
	private BankName bankName;

	@NotBlank(message = "Account number is required")
	@Pattern(regexp = "\\d{9,18}", message = "Account number must be between 9 and 18 digits")
	private String accountNumber;

	@NotBlank(message = "Branch name is required")
	private String branchName;

	@NotBlank(message = "IFSC code is required")
	@Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code format")
	private String ifscCode;

	private String passbookAttachment;
}
