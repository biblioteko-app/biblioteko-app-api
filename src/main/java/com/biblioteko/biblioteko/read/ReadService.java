package com.biblioteko.biblioteko.read;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.biblioteko.biblioteko.book.Book;
import com.biblioteko.biblioteko.book.BookDTO;
import com.biblioteko.biblioteko.user.User;
import com.biblioteko.biblioteko.user.UserService;
import com.biblioteko.biblioteko.utils.BookMapper;
import com.biblioteko.biblioteko.book.BookService;
import com.biblioteko.biblioteko.exception.BookNotFoundException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;

@Service
public class ReadService {

    @Autowired
    private ReadRepository readRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    public Read createRead(Integer readPages, User user, Book book) {    
        Read read = new Read(user, book, readPages);
        return readRepository.save(read);

    }

    public ReadDTO convertReadToDto(Read read){
        return new ReadDTO(read.getId(), read.getUser(), read.getBook(), read.getReadPages());

    }

    public ReadDTO addBookToReadingList(ReadPagesDTO readPagesDTO, UUID userId, UUID bookId) throws UserNotFoundException, BookNotFoundException {
        User user = userService.findUserById(userId);
        Book book = bookService.findBookById(bookId);

        if(readPagesDTO.getReadPages()<= 0 || readPagesDTO.getReadPages() > book.getPages()){
            throw new IllegalArgumentException("Quantidade de paginas lidas invalida");
        }

        Read read = createRead(readPagesDTO.getReadPages(), user, book);

        userService.addRead(read, user);
        
        return convertReadToDto(read);
    }

    public Set<BookDTO> getBooksToReadingList(UUID userId) throws UserNotFoundException, BookNotFoundException{
        User user = userService.findUserById(userId);
    
        return user.getReadingList().stream().map(r -> BookMapper.convertToBookDTO(r.getBook())).collect(Collectors.toSet());
    } 
}
