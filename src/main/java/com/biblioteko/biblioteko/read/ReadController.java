package com.biblioteko.biblioteko.read;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.biblioteko.biblioteko.book.BookDTO;
import com.biblioteko.biblioteko.exception.BookNotFoundException;
import com.biblioteko.biblioteko.exception.ReadCompletedException;
import com.biblioteko.biblioteko.exception.ReadNotFoundException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;
import com.biblioteko.biblioteko.exception.UserUnauthorizedException;
import com.biblioteko.biblioteko.security.services.AuthUserService;

@RestController
@RequestMapping("/api/read")
public class ReadController {
	@Autowired
	ReadService readService;

	@SuppressWarnings("unused")
	@Autowired
	private AuthUserService authUserService;

	@PostMapping("/{user_id}/{book_id}")
	@PreAuthorize("@authUserService.checkId(#userId)")
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
	@PreAuthorize("@authUserService.checkId(#userId)")
	public ResponseEntity<?> getBooksToReadingList(@PathVariable("user_id") UUID userId) {
		try{   
			Set<BookDTO> booksDTO = readService.getBooksToReadingList(userId);
			return new ResponseEntity<>(booksDTO, HttpStatus.CREATED);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}catch(UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch(Exception e){
			return new ResponseEntity<String>("Erro ao listar leituras." + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{user_id}/{read_id}")
	@PreAuthorize("@authUserService.checkId(#userId)")
	public ResponseEntity<?> alterReadProgress(@PathVariable("user_id") UUID userId, @PathVariable("read_id") UUID readId, @RequestBody Integer readPages){
		try {
			ReadDTO readDTO = readService.alterProgress(userId, readId, readPages);
			return new ResponseEntity<ReadDTO>(readDTO, HttpStatus.OK);
		}catch(UserNotFoundException | ReadNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch(UserUnauthorizedException | IllegalArgumentException | ReadCompletedException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<String>("Erro ao alterar progresso da leitura." + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@GetMapping("/{user_id}/list")
	@PreAuthorize("@authUserService.checkId(#userId)")
	public ResponseEntity<?> getReadingList(@PathVariable("user_id") UUID userId) {
		try{   
			Set<ReadDTO> readDTO = readService.getReadingList(userId);
			return new ResponseEntity<>(readDTO, HttpStatus.CREATED);
		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}catch(UserNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch(Exception e){
			return new ResponseEntity<String>("Erro ao listar leituras." + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
