package com.farmer.farmermanagement.entity;

import java.time.LocalDate;
import java.util.List;

import com.farmer.farmermanagement.enums.Document;
import com.farmer.farmermanagement.enums.Education;
import com.farmer.farmermanagement.enums.Gender;
import com.farmer.farmermanagement.enums.PortalAccess;
import com.farmer.farmermanagement.enums.PortalRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Farmer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String firstName;

	private String MiddleName;

	@NotBlank
	private String lastName;

	@NotBlank(message = "Aadhar number is required")
	@Column(unique = true)
	private String aadharNumber;

	@Email
	@NotBlank(message = "Email is required")
	private String email;

	@NotBlank(message = "Phone number is required")
	@Column(unique = true)
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@NotNull
	private Gender gender;

	@Enumerated(EnumType.STRING)
	@NotNull
	private Education education;

	@Enumerated(EnumType.STRING)
	private Document document;

	@NotBlank(message = "Document path is required")
	private String documentPath;

	@NotNull(message = "Date of birth is required")
	private LocalDate dateOfBirth;

	@Enumerated(EnumType.STRING)
	private PortalAccess portalAccess;

	@Enumerated(EnumType.STRING)
	private PortalRole portalRole;

	@NotBlank(message = "Farmer type is required")
	private String farmerType;

	@Embedded
	private Address address;

	@OneToOne(mappedBy = "farmer", cascade = CascadeType.ALL, orphanRemoval = true)
	private BankDetails bankDetails;

	@OneToOne(mappedBy = "farmer", cascade = CascadeType.ALL, orphanRemoval = true)
	private LandDetails landDetails;

	@OneToMany(mappedBy = "farmer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Crop> crops;
}
