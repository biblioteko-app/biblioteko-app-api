package com.biblioteko.biblioteko.reviewClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteko.biblioteko.book.Book;
import com.biblioteko.biblioteko.book.BookService;
import com.biblioteko.biblioteko.studentClass.StudentClass;
import com.biblioteko.biblioteko.studentClass.StudentClassService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<String> createReview(@RequestParam Long bookId, @RequestParam Long studentId,
            @RequestParam String comment) {
        Book book = BookService.findById(bookId);

        StudentClass student = StudentClassService.findById(studentId);

        if (book == null || student == null) {
            return new ResponseEntity<>("Livro ou ID de estudante inválido.", HttpStatus.BAD_REQUEST);
        }

        reviewService.createReview(book, student, comment);

        return new ResponseEntity<>("Avaliação criada com sucesso!.", HttpStatus.CREATED);
    }

}
