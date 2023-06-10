package com.biblioteko.biblioteko.security.response;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoResponse {
	private UUID id;
	private String email;
	
	@Setter(AccessLevel.NONE)
	private List<String> securityRoles;

}
