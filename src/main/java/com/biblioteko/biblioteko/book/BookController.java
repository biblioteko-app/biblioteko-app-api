package com.biblioteko.biblioteko.book;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.biblioteko.biblioteko.exception.BookAlreadyFavoritedException;
import com.biblioteko.biblioteko.exception.BookNotFoundException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;
import com.biblioteko.biblioteko.exception.UserUnauthorizedException;
import com.biblioteko.biblioteko.security.services.AuthUserService;

import org.springframework.stereotype.Controller;

@RestController
@RequestMapping("/api/book")
public class BookController {
    @Autowired 
    private BookService bookService;
    
    @SuppressWarnings("unused")
	@Autowired
    private AuthUserService authUserService;

	@PostMapping("/{user_id}")
	@PreAuthorize("@authUserService.checkId(#userId) and @authUserService.isProf()")
    public ResponseEntity<?> createBook(@RequestBody NewBookDTO newBookDTO, @PathVariable("user_id") UUID userId) {
        try{   
            BookDTO bookDTO = bookService.createBook(newBookDTO, userId);
            return new ResponseEntity<>(bookDTO.getId(), HttpStatus.CREATED);
        }catch(UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(UserUnauthorizedException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
        catch(IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch(Exception e){
            return new ResponseEntity<String>("Erro ao criar o livro.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
   
	}

	@PutMapping("/{user_id}/{book_id}")
	@PreAuthorize("@authUserService.checkId(#userId) and @authUserService.isProf()")
	public ResponseEntity<?> editBook(@RequestBody NewBookDTO newBookDTO, @PathVariable("user_id") UUID userId, @PathVariable("book_id") UUID bookId){
		try{   
            BookDTO bookDTO = bookService.editBook(newBookDTO, userId, bookId);
            return new ResponseEntity<>(bookDTO.getId(), HttpStatus.CREATED);
        }catch(UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(UserUnauthorizedException e){
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
	@PreAuthorize("@authUserService.checkId(#userId) and @authUserService.isProf()")
    public ResponseEntity<?> removeBook(@PathVariable("user_id") UUID userId, @PathVariable("book_id") UUID bookId) {
        try {
            bookService.removeBook(bookId, userId);
            return new ResponseEntity<>("Livro removido com sucesso!", HttpStatus.OK);
        } catch (BookNotFoundException | UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(UserUnauthorizedException e) {
        	return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao remover o livro.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/books/{user_id}")
    public ResponseEntity<?> getAllBooks(@PathVariable("user_id") UUID userId) {
        try {
            List<BookDTO> booksListDTO = bookService.getAllBooks(userId);
            return new ResponseEntity<>(booksListDTO, HttpStatus.OK);
        } catch(UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>("Erro ao obter livros.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{user_id}/{book_id}/favorite")
    public ResponseEntity<?> starredBook(@PathVariable("user_id") UUID userId, @PathVariable("book_id") UUID bookId){
        try{
            bookService.starredBook(userId, bookId);
            return new ResponseEntity<>("Livro favoritado", HttpStatus.OK);
         }catch(UserNotFoundException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
         }catch(BookNotFoundException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(BookAlreadyFavoritedException e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>("Erro ao favoritar o livro.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{user_id}/{book_id}/unfavorite")
    public ResponseEntity<?> unstarredBook(@PathVariable("user_id") UUID userId, @PathVariable("book_id") UUID bookId){
        try{
            bookService.unstarredBook(userId, bookId);
            return new ResponseEntity<>("Livro favoritado", HttpStatus.OK);
         }catch(UserNotFoundException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
         }catch(BookNotFoundException e ){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch(BookAlreadyFavoritedException e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>("Erro ao desfavoritar o livro.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/my-books/{user_id}")
    public ResponseEntity<?> getAllMyBooks(@PathVariable("user_id") UUID userId) {
        try {
            List<BookDTO> booksListDTO = bookService.getAllMyBooks(userId);
            return new ResponseEntity<>(booksListDTO, HttpStatus.OK);
        } catch(UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            return new ResponseEntity<>("Erro ao obter livros.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}