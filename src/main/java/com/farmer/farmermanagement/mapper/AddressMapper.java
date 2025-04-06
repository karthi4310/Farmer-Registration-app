package com.farmer.farmermanagement.mapper;

import org.mapstruct.Mapper;

import com.farmer.farmermanagement.dto.AddressDto;
import com.farmer.farmermanagement.entity.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {
	AddressDto toDto(Address address);

	Address toEntity(AddressDto addressDto);

}
