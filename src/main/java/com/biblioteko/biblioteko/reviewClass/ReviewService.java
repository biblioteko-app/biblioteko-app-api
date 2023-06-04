package com.biblioteko.biblioteko.reviewClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteko.biblioteko.book.Book;
import com.biblioteko.biblioteko.studentClass.StudentClass;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void createReview(Book book, StudentClass student, String comment) {
        Review review = new Review();
        review.setBook(book);
        review.setStudent(student);
        review.setComment(comment);
        reviewRepository.save(review);
    }

}
