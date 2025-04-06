package com.farmer.farmermanagement.dto;

import com.farmer.farmermanagement.enums.CropType;
import com.farmer.farmermanagement.enums.IrrigationSource;
import com.farmer.farmermanagement.enums.SoilTest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LandDetailsDto {
	private Long id;
	private String surveyNumber;
	private double landSize;
	private CropType cropType;
	private SoilTest soilTest;
	private String soilTestCertificate;
	private String geoTag;
	private Double latitude;
	private Double longitude;
	private IrrigationSource irrigationSource;
	private String borewellDischarge;
	private String borewellLocation;
}
