package com.biblioteko.biblioteko.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String name;
    private String role;

    public UserDTO(String name, String role) {
        this.name = name;
        this.role = role;
    }
    
}
