package com.biblioteko.biblioteko.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserDTO {
    private String name;
    private String role;

    public NewUserDTO(String name, String role) {
        this.name = name;
        this.role = role;
    }
    
}
