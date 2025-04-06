package com.farmer.farmermanagement.mapper;

import org.mapstruct.Mapper;

import com.farmer.farmermanagement.dto.CropDto;
import com.farmer.farmermanagement.entity.Crop;

@Mapper(componentModel = "spring")
public interface CropMapper {

	CropDto toDto(Crop crop);

	Crop toEntity(CropDto cropDTO);
}
