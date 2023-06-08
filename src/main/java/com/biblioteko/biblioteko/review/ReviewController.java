package com.biblioteko.biblioteko.review;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.biblioteko.biblioteko.book.Book;
import com.biblioteko.biblioteko.exception.BookNotFoundException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;
import com.biblioteko.biblioteko.security.services.AuthUserService;
import com.biblioteko.biblioteko.user.User;


@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	
	@Autowired
    private ReviewService reviewService;

	
	@Autowired
    private AuthUserService authUserService;

 
    @PostMapping("/{user_id}/{book_id}")
    @PreAuthorize("@authUserService.checkId(#userId)")
    public ResponseEntity<String> createReview(@PathVariable("book_id") UUID bookId, @PathVariable("user_id") UUID userId,
	@RequestBody ReviewDTO reviewDTO) {
    	
    	try {
    		reviewService.createReview(bookId, userId, reviewDTO);
    	}catch(BookNotFoundException | UserNotFoundException e) {
    		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    	}catch(IllegalArgumentException e) {
    		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    	}catch(Exception e) {
    		return new ResponseEntity<>("Não foi possível registrar avaliação.", HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	return new ResponseEntity<>("Avaliação registrada com sucesso!.", HttpStatus.CREATED);
    }

}
