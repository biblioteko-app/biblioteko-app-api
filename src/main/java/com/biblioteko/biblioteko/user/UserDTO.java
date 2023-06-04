package com.biblioteko.biblioteko.user;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	
	private UUID id;
	
    private String name;
    
    private String email;
    
    private String role;

    private UUID id;

    private Set<UUID> readingList;

    public UserDTO(UUID id, String name, String email, String password, String role, Set<UUID> readingList) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.id = id;
        this.readingList = readingList;
    }

}
