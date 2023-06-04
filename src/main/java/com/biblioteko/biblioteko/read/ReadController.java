package com.biblioteko.biblioteko.read;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.biblioteko.biblioteko.book.BookDTO;
import com.biblioteko.biblioteko.exception.BookNotFoundException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;

@Controller
@RequestMapping("/read")
public class ReadController {
    @Autowired
    ReadService readService;

   @PostMapping("/{user_id}/{book_id}")
   public ResponseEntity<?> addBookToReadingList(@RequestBody ReadPagesDTO readPagesDTO, @PathVariable("user_id") UUID userId, @PathVariable("book_id") UUID bookId) {
	   try{   
		   ReadDTO readDTO = readService.addBookToReadingList(readPagesDTO, userId, bookId);
		   return new ResponseEntity<>(readDTO.getId(), HttpStatus.CREATED);
	   }catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}catch(UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch(BookNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch(Exception e){
		   return new ResponseEntity<String>("Erro ao adicionar livro na lista de leitura." + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	   }
  }

  @GetMapping("/{user_id}")
   public ResponseEntity<?> getBooksToReadingList(@PathVariable("user_id") UUID userId) {
	   try{   
		   Set<BookDTO> booksDTO = readService.getBooksToReadingList(userId);
		   return new ResponseEntity<>(booksDTO, HttpStatus.CREATED);
	   }catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}catch(UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch(Exception e){
		   return new ResponseEntity<String>("Erro ao adicionar livro na lista de leitura." + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	   }
  }
    
}
