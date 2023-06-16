package com.biblioteko.biblioteko.review;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteko.biblioteko.book.Book;
import com.biblioteko.biblioteko.book.BookService;
import com.biblioteko.biblioteko.exception.BookNotFoundException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;
import com.biblioteko.biblioteko.user.User;
import com.biblioteko.biblioteko.user.UserService;

@Service
public class ReviewService {
	
	@Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    public void createReview(UUID bookId, UUID userId, ReviewDTO reviewDTO) throws UserNotFoundException, BookNotFoundException {
        User user = userService.findUserById(userId);
        Book book = bookService.findBookById(bookId);

        if(reviewDTO.getStars() < 0 || reviewDTO.getStars() > 5) throw new IllegalArgumentException("Quantidade de estrelas invalida");

        Review review = new Review();
        review.setBook(book);
        review.setUser(user);
        review.setStars(reviewDTO.getStars());
        reviewRepository.save(review);

        bookService.updateRatingBook(book, reviewDTO.getStars()); 
        
    }

}
