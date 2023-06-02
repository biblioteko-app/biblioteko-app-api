package com.biblioteko.biblioteko.book;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biblioteko.biblioteko.exception.BookNotFoundException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;
import com.biblioteko.biblioteko.exception.UserUnauthorized;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired 
    private BookService bookService;

	@PostMapping("/{user_id}")
    public ResponseEntity<?> createBook(@RequestBody NewBookDTO newBookDTO, @PathVariable("user_id") UUID userId) {
        try{   
            BookDTO bookDTO = bookService.createBook(newBookDTO, userId);
            return new ResponseEntity<>(bookDTO.getId(), HttpStatus.CREATED);
        }catch(UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(UserUnauthorized e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<String>("Erro ao criar o livro.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
   
	}

	@PutMapping("/{user_id}/{book_id}")
	public ResponseEntity<?> editBook(@RequestBody NewBookDTO newBookDTO, @PathVariable("user_id") UUID userId, @PathVariable("book_id") UUID bookId){
		try{   
            BookDTO bookDTO = bookService.editBook(newBookDTO, userId, bookId);
            return new ResponseEntity<>(bookDTO.getId(), HttpStatus.CREATED);
        }catch(UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(UserUnauthorized e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<String>("Erro ao editar o livro.", HttpStatus.INTERNAL_SERVER_ERROR);
        }


	}

	@GetMapping("/{book_id}")
    public ResponseEntity<?> getBookDetails(@PathVariable("book_id") UUID bookId) {
        try {
            BookDTO bookDTO = bookService.getBookDetails(bookId);
            return new ResponseEntity<>(bookDTO, HttpStatus.OK);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao obter detalhes do livro.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@DeleteMapping("/{user_id}/{book_id}")
    public ResponseEntity<?> removeBook(@PathVariable("user_id") UUID userId, @PathVariable("book_id") UUID bookId) {
        try {
            bookService.removeBook(bookId, userId);
            return new ResponseEntity<>("Livro removido com sucesso!", HttpStatus.OK);
        } catch (BookNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao remover o livro.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}