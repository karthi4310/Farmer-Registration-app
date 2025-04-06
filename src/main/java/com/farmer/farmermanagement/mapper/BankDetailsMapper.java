package com.farmer.farmermanagement.mapper;

import org.mapstruct.Mapper;

import com.farmer.farmermanagement.dto.BankDetailsDto;
import com.farmer.farmermanagement.entity.BankDetails;

@Mapper(componentModel = "spring")
public interface BankDetailsMapper {
	BankDetails toEntity(BankDetailsDto dto);

	BankDetailsDto toDto(BankDetails entity);
}
