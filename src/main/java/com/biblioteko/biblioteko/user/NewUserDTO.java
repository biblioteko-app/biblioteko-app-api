package com.biblioteko.biblioteko.user;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDTO {
	
	@NotBlank
    @Size(min = 3, max = 100)
    private String name;
    
	@NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    
    @NotBlank
    private String role;
    
}
