package com.farmer.farmermanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.farmer.farmermanagement.dto.FarmerDto;
import com.farmer.farmermanagement.entity.Farmer;

@Mapper(componentModel = "spring", uses = { AddressMapper.class, BankDetailsMapper.class, LandDetailsMapper.class,
		CropMapper.class, })
public interface FarmerMapper {

	// --- Farmer <-> FarmerDto ---
	@Mappings({ @Mapping(target = "address", source = "address"),
			@Mapping(target = "bankDetails", source = "bankDetails"),
			@Mapping(target = "landDetails", source = "landDetails"), @Mapping(target = "crops", source = "crops"), })
	Farmer toEntity(FarmerDto dto);

	@Mappings({ @Mapping(target = "address", source = "address"),
			@Mapping(target = "bankDetails", source = "bankDetails"),
			@Mapping(target = "landDetails", source = "landDetails"), @Mapping(target = "crops", source = "crops"), })
	FarmerDto toDto(Farmer entity);

}
