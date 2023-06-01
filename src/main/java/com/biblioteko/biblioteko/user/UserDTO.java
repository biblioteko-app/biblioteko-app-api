package com.biblioteko.biblioteko.user;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
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

    private Set<UUID> redingList;

    public UserDTO(UUID id, String name, String email, String password, String role, Set<UUID> redingList) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.id = id;
        this.redingList = redingList;
    }
}
