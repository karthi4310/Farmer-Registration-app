package com.farmer.farmermanagement.dto;

import java.time.LocalDate;
import java.util.List;

import com.farmer.farmermanagement.enums.Document;
import com.farmer.farmermanagement.enums.Education;
import com.farmer.farmermanagement.enums.Gender;
import com.farmer.farmermanagement.enums.PortalAccess;
import com.farmer.farmermanagement.enums.PortalRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FarmerDto {

	private Long id;

	@NotBlank
	private String firstName;

	private String MiddleName;

	@NotBlank
	private String lastName;

	@NotBlank
	private String aadharNumber;

	@Email
	@NotBlank
	private String email;

	@NotBlank
	private String phoneNumber;

	@NotNull
	private Gender gender;

	@NotNull
	private Education education;

	private Document document;

	@NotBlank
	private String documentPath;

	@NotNull
	private LocalDate dateOfBirth;

	private PortalAccess portalAccess;

	private PortalRole portalRole;

	@NotBlank
	private String farmerType;

	private AddressDto address;

	private BankDetailsDto bankDetails;

	private LandDetailsDto landDetails;

	private List<CropDto> crops;
}
