package com.biblioteko.biblioteko.user;
import java.util.Set;
import java.util.UUID;

import com.biblioteko.biblioteko.book.BookDTO;

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

    private Set<BookDTO> readingList;

    private Set<BookDTO> starredBooks;

}
