package com.farmer.farmermanagement.dto;

import java.time.LocalDate;

import com.farmer.farmermanagement.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private LocalDate dateOfBirth;
	private String gender;
	private String token;

	public static UserResponseDTO fromEntity(User user, String token) {
		return UserResponseDTO.builder().id(user.getId()).firstName(user.getFirstName()).lastName(user.getLastName())
				.email(user.getEmail()).phoneNumber(user.getPhoneNumber()).dateOfBirth(user.getDateOfBirth())
				.gender(user.getGender()).token(token).build();
	}
}
