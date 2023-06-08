package com.biblioteko.biblioteko.utils;

import com.biblioteko.biblioteko.book.Book;
import com.biblioteko.biblioteko.book.BookDTO;

public class BookMapper {

    public static BookDTO convertToBookDTO(Book book){
        return new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(),
                           book.getEdition(), book.getSynopsis(), book.getRating(), book.getPhoto(), 
                           book.getPages(), book.getAccessLink());

    }
    
}
