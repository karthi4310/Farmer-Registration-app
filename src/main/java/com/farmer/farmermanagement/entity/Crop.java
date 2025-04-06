package com.farmer.farmermanagement.entity;

import com.farmer.farmermanagement.enums.CropType;
import com.farmer.farmermanagement.enums.IrrigationSource;
import com.farmer.farmermanagement.enums.SoilTest;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Crop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Photo is required")
	@Column(nullable = false)
	private String photo;

	@NotBlank(message = "Crop name is required")
	@Column(nullable = false)
	private String cropName;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Crop type is required")
	@Column(nullable = false)
	private CropType cropType;

	@Positive(message = "Area in acres must be positive")
	@Column(nullable = false)
	private double areaInAcres;

	@NotBlank(message = "Survey number is required")
	@Column(nullable = false, unique = true)
	private String surveyNumber;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Soil test is required")
	@Column(nullable = false)
	private SoilTest soilTest;

	@NotBlank(message = "Soil test certificate is required")
	private String soilTestCertificate;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Irrigation source is required")
	@Column(nullable = false)
	private IrrigationSource irrigationSource;

	@NotBlank(message = "Geotag (Address) is required")
	@Column(nullable = false)
	private String geoTag;

	private Double latitude;
	private Double longitude;

	@PositiveOrZero(message = "Net income cannot be negative")
	@Column(nullable = false)
	private double netIncome;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "farmer_id", nullable = false)
	@JsonIgnore
	private Farmer farmer;
}
