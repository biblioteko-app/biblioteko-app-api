package com.biblioteko.biblioteko.review;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteko.biblioteko.book.Book;
import com.biblioteko.biblioteko.book.BookService;
import com.biblioteko.biblioteko.exception.BookNotFoundException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;
import com.biblioteko.biblioteko.security.services.AuthUserService;
import com.biblioteko.biblioteko.user.User;
import com.biblioteko.biblioteko.user.UserService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	
	@Autowired
    private ReviewService reviewService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private AuthUserService authUserService;

 
    @PostMapping
    @PreAuthorize("@authUserService.checkId(#studentId)")
    public ResponseEntity<String> createReview(@RequestParam UUID bookId, @RequestParam UUID studentId,
            @RequestParam String comment) {
    	
    	try {

    		Book book = bookService.findById(bookId);

    		User student = userService.findUserById(studentId);

    		reviewService.createReview(book, student, comment);

    	}catch(BookNotFoundException | UserNotFoundException e) {
    		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    	}catch(IllegalArgumentException e) {
    		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    	}catch(Exception e) {
    		return new ResponseEntity<>("Não foi possível enviar avaliação.", HttpStatus.INTERNAL_SERVER_ERROR);
    	}

    	return new ResponseEntity<>("Avaliação criada com sucesso!.", HttpStatus.CREATED);
    }

}
