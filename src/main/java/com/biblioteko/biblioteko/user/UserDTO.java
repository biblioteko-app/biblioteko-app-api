package com.biblioteko.biblioteko.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	
    private String name;
    
    private String email;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    
    private String role;

    private UUID id;

    public UserDTO(UUID id, String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.id = id;
    }

    
}
