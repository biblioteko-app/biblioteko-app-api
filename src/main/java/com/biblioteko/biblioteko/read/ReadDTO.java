package com.biblioteko.biblioteko.read;
import java.util.UUID;
import com.biblioteko.biblioteko.book.BookDTO;
import com.biblioteko.biblioteko.user.UserDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadDTO {

    private UUID id;

    private UserDTO user;
    
    private BookDTO book;
    
    private Integer readPages;

    public ReadDTO(UUID id, UserDTO user, BookDTO book, Integer readPages) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.readPages = readPages;
    } 
}
