package com.biblioteko.biblioteko.book;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteko.biblioteko.exception.BookNotFoundException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;
import com.biblioteko.biblioteko.exception.UserUnauthorized;
import com.biblioteko.biblioteko.user.User;
import com.biblioteko.biblioteko.user.UserService;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserService userService;

    public BookDTO createBook(NewBookDTO newBookDTO, UUID userId) throws UserNotFoundException, UserUnauthorized{
        User user = userService.findUserById(userId);

        if(user.getRole().equals("PROFESSOR")){
            if(newBookDTO.getPages() <= 0) throw new IllegalArgumentException("Quantidade de pages inválida!");

            Integer edition = newBookDTO.getEdition();

            if(edition == null || edition == 0){
                edition = 1;
            }

            Book book = new Book(newBookDTO.getTitle(), newBookDTO.getAuthor(), newBookDTO.getGender(), 
                                edition, newBookDTO.getSynopsis(), newBookDTO.getRating(), 
                                newBookDTO.getPhoto(), newBookDTO.getPages(), newBookDTO.getAccessLink(), user);

            return convertToBookDTO(bookRepository.save(book));
        }else{
            throw new UserUnauthorized("Usuario sem permissão!");
        }
        
    }

    public Book findBookById(UUID bookId) throws BookNotFoundException {
        Optional<Book> book = this.bookRepository.findById(bookId);
        if(!book.isPresent()) throw new BookNotFoundException("Livro não encontrado.");

        return book.get();
    }

    public BookDTO convertToBookDTO(Book book){
        return new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getGender(),
                           book.getEdition(), book.getSynopsis(), book.getRating(), book.getPhoto(), 
                           book.getPages(), book.getAccessLink());

    }

    public BookDTO editBook(NewBookDTO newBookDTO, UUID userId, UUID bookId) throws UserNotFoundException, UserUnauthorized, BookNotFoundException{
        User user = userService.findUserById(userId);
        Book book = findBookById(bookId);
        
        if(user.getRole().equals("PROFESSOR") && book.getOwner().getId().equals(user.getId())){

            if(newBookDTO.getPages() <= 0) throw new IllegalArgumentException("Quantidade de paginas inválida!");

            Integer edition = newBookDTO.getEdition();

            if(edition == null || edition == 0){
                edition = 1;
            }

            book.setTitle(newBookDTO.getTitle());
            book.setAuthor(newBookDTO.getAuthor());
            book.setEdition(edition);
            book.setGender(newBookDTO.getGender());
            book.setSynopsis(newBookDTO.getSynopsis());
            book.setRating(newBookDTO.getRating());
            book.setPhoto(newBookDTO.getPhoto());
            book.setPages(newBookDTO.getPages());
            book.setAccessLink(newBookDTO.getAccessLink());
            
            return convertToBookDTO(bookRepository.save(book));
        }else{
            throw new UserUnauthorized("Usuario sem permissão!");
        }
    }

    public BookDTO getBookDetails(UUID bookId) throws BookNotFoundException {
        Book book = findBookById(bookId);

        return convertToBookDTO(book);
    }

    public void removeBook(UUID bookId, UUID userId) throws BookNotFoundException, UserNotFoundException, UserUnauthorized{
        User user = userService.findUserById(userId);
        Book book = findBookById(bookId);
        
        if(user.getRole().equals("PROFESSOR") && book.getOwner().getId().equals(user.getId())){
            
           bookRepository.delete(book);
    }else{
        throw new UserUnauthorized("Não possui autorização para realizar essa remoção!");

    }
}

    public Book findById(UUID bookId) throws BookNotFoundException {
        
    	if(!bookRepository.existsById(bookId)) throw new BookNotFoundException("Livro não encontrado.");
    	
    	return bookRepository.findById(bookId).get();
    	
    }
  
}
