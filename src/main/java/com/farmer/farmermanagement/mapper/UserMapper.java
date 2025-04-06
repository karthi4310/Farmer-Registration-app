package com.farmer.farmermanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.farmer.farmermanagement.dto.UserDTO;
import com.farmer.farmermanagement.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDTO toDto(User user);

	@Mappings({ @Mapping(ignore = true, target = "id") })
	User toEntity(UserDTO userDto);

}
