package com.farmer.farmermanagement.entity;

import com.farmer.farmermanagement.enums.CropType;
import com.farmer.farmermanagement.enums.IrrigationSource;
import com.farmer.farmermanagement.enums.SoilTest;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LandDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Survey number is required")
	@Column(nullable = false, unique = true)
	private String surveyNumber;

	@Positive(message = "Land size must be greater than zero")
	@Column(nullable = false)
	private double landSize;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Crop type is required")
	@Column(nullable = false)
	private CropType cropType;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Soil test is required")
	@Column(nullable = false)
	private SoilTest soilTest;

	@NotBlank(message = "Soil test certificate is required")
	private String soilTestCertificate;

	@NotBlank(message = "GeoTag (Address) is required")
	@Column(nullable = false)
	private String geoTag;

	private Double latitude;
	private Double longitude;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Irrigation source is required")
	private IrrigationSource irrigationSource;

	private String borewellDischarge;
	private String borewellLocation;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "farmer_id", nullable = false, unique = true)
	@JsonIgnore
	private Farmer farmer;
}
