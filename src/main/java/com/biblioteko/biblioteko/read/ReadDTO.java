package com.biblioteko.biblioteko.read;
import java.util.UUID;
import com.biblioteko.biblioteko.book.Book;
import com.biblioteko.biblioteko.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadDTO {

    private UUID id;

    private User user;
    
    private Book book;
    
    private Integer readPages;

    public ReadDTO(UUID id, User user, Book book, Integer readPages) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.readPages = readPages;
    } 
}
