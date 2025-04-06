package com.farmer.farmermanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.farmer.farmermanagement.dto.LandDetailsDto;
import com.farmer.farmermanagement.entity.LandDetails;

@Mapper(componentModel = "spring")
public interface LandDetailsMapper {

	LandDetailsMapper INSTANCE = Mappers.getMapper(LandDetailsMapper.class);

	LandDetailsDto toDto(LandDetails landDetails);

	LandDetails toEntity(LandDetailsDto landDetailsDto);
}
