package com.biblioteko.biblioteko.book;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.sql.ast.tree.expression.Star;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteko.biblioteko.exception.BookAlreadyFavoritedException;
import com.biblioteko.biblioteko.exception.BookNotFoundException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;
import com.biblioteko.biblioteko.exception.UserUnauthorizedException;
import com.biblioteko.biblioteko.user.User;
import com.biblioteko.biblioteko.user.UserService;
import com.biblioteko.biblioteko.utils.BookMapper;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserService userService;

    public BookDTO createBook(NewBookDTO newBookDTO, UUID userId) throws UserNotFoundException, UserUnauthorizedException{
        User user = userService.findUserById(userId);

        if(user.getRole().equals("PROFESSOR")){
            if(newBookDTO.getPages() <= 0) throw new IllegalArgumentException("Quantidade de pages inválida!");

            Integer edition = newBookDTO.getEdition();

            if(edition == null || edition == 0){
                edition = 1;
            }

            Book book = new Book(newBookDTO.getTitle(), newBookDTO.getAuthor(), newBookDTO.getGender(), 
                                edition, newBookDTO.getSynopsis(), 
                                newBookDTO.getPhoto(), newBookDTO.getPages(), newBookDTO.getAccessLink(), user);

            return BookMapper.convertToBookDTO(bookRepository.save(book));
        }else{
            throw new UserUnauthorizedException("Usuario sem permissão!");
        }
        
    }

    public Book findBookById(UUID bookId) throws BookNotFoundException {
        Optional<Book> book = this.bookRepository.findById(bookId);
        if(!book.isPresent()) throw new BookNotFoundException("Livro não encontrado.");

        return book.get();
    }


    public BookDTO editBook(NewBookDTO newBookDTO, UUID userId, UUID bookId) throws UserNotFoundException, UserUnauthorizedException, BookNotFoundException{
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
            book.setGenre(newBookDTO.getGender());
            book.setSynopsis(newBookDTO.getSynopsis());
            book.setPhoto(newBookDTO.getPhoto());
            book.setPages(newBookDTO.getPages());
            book.setAccessLink(newBookDTO.getAccessLink());
            
            return BookMapper.convertToBookDTO(bookRepository.save(book));
        }else{
            throw new UserUnauthorizedException("Usuario sem permissão!");
        }
    }

    public BookDTO getBookDetails(UUID bookId) throws BookNotFoundException {
        Book book = findBookById(bookId);

        return BookMapper.convertToBookDTO(book);
    }

    public void removeBook(UUID bookId, UUID userId) throws BookNotFoundException, UserNotFoundException, UserUnauthorizedException {
        User user = userService.findUserById(userId);
        Book book = findBookById(bookId);
        
        if(user.getRole().equals("PROFESSOR") && book.getOwner().getId().equals(user.getId())){
            
           bookRepository.delete(book);
    }else{
        throw new UserUnauthorizedException("Não possui autorização para realizar essa remoção!");

    }
}

    public Book findById(UUID bookId) throws BookNotFoundException {
        
    	if(!bookRepository.existsById(bookId)) throw new BookNotFoundException("Livro não encontrado.");
    	
    	return bookRepository.findById(bookId).get();
    	
    }

    public List<BookDTO> getAllBooks(UUID userId) throws UserNotFoundException {
        userService.findUserById(userId);
        return  bookRepository.findAll().stream().map(b -> BookMapper.convertToBookDTO(b)).collect(Collectors.toList());
    }

    public void updateRatingBook(Book book, Float stars){
        book.updateRating(stars);
        bookRepository.save(book);

    }

    public void starredBook(UUID userId, UUID bookId) throws UserNotFoundException, BookNotFoundException, BookAlreadyFavoritedException {
        User user = userService.findUserById(userId);
        Book book = findBookById(bookId);

        if (user.getStarredBooks().contains(book)) {
            throw new BookAlreadyFavoritedException("Livro já está na lista de favoritos");
        }

        userService.addBookToStarredList(user, book);
        
    }

  
}
