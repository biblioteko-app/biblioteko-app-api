package com.biblioteko.biblioteko.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteko.biblioteko.book.Book;
import com.biblioteko.biblioteko.user.User;

@Service
public class ReviewService {
	
	@Autowired
    private ReviewRepository reviewRepository;

    public void createReview(Book book, User student, String comment) {
    	
    	if(comment.isEmpty() || comment.isBlank()) throw new IllegalArgumentException("Sua avaliação deve conter um comentário.");
    	
        Review review = new Review();
        review.setBook(book);
        review.setStudent(student);
        review.setComment(comment);
        reviewRepository.save(review);
        
    }

}
