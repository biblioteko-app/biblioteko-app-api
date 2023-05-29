package com.biblioteko.biblioteko.user;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String role;
    private UUID id;

    public UserDTO(String name, String role, UUID id) {
        this.name = name;
        this.role = role;
        this.id = id;
    }
    
}
