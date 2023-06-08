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
import com.biblioteko.biblioteko.exception.ReadCompletedException;
import com.biblioteko.biblioteko.exception.ReadNotFoundException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;
import com.biblioteko.biblioteko.exception.UserUnauthorizedException;

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
    
    public ReadDTO alterProgress(UUID userId, UUID readId, Integer readPages) throws UserNotFoundException, UserUnauthorizedException,
    IllegalArgumentException, ReadNotFoundException, ReadCompletedException {
    	
    	if(!userService.existsById(userId)) throw new UserNotFoundException("Usuário inexistente.");
    	if(!readRepository.existsById(readId)) throw new ReadNotFoundException("Leitura inexistente.");
    	
    	Read read = readRepository.findById(readId).get();
    	
    	if(!read.getUser().getId().equals(userId)) throw new UserUnauthorizedException("Você não possui autorização para realizar esta ação.");
    	
    	if(read.isFinalized()) throw new ReadCompletedException("Esta leitura já foi finalizada");
    	
    	if(readPages <= read.getReadPages()) throw new IllegalArgumentException("O número de páginas deve ser maior que o número atual.");
    	
    	if(readPages > read.getBook().getPages()) throw new IllegalArgumentException("O número de páginas não pode ser maior que o total de páginas do livro.");
    	
    	read.setReadPages(readPages);
    	readRepository.save(read);
    	return convertReadToDto(read);
    }

}
