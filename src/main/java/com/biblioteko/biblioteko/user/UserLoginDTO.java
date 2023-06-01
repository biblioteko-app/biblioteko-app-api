package com.biblioteko.biblioteko.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserLoginDTO {
	@NotBlank
	private String email;

	@NotBlank
	private String password;

	
}