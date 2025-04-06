package com.farmer.farmermanagement.dto;

import com.farmer.farmermanagement.enums.CropType;
import com.farmer.farmermanagement.enums.IrrigationSource;
import com.farmer.farmermanagement.enums.SoilTest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CropDto {

	@NotBlank(message = "Photo is required")
	private String photo;

	@NotBlank(message = "Crop name is required")
	private String cropName;

	@NotNull(message = "Crop type is required")
	private CropType cropType;

	@Positive(message = "Area in acres must be positive")
	private double areaInAcres;

	@NotBlank(message = "Survey number is required")
	private String surveyNumber;

	@NotNull(message = "Soil test is required")
	private SoilTest soilTest;

	@NotBlank(message = "Soil test certificate is required")
	private String soilTestCertificate;

	@NotNull(message = "Irrigation source is required")
	private IrrigationSource irrigationSource;

	@NotBlank(message = "Geotag is required")
	private String geoTag;

	private Double latitude;
	private Double longitude;

	@PositiveOrZero(message = "Net income cannot be negative")
	private double netIncome;
}
