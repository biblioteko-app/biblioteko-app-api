package com.biblioteko.biblioteko.reviewClass;

import java.util.UUID;

import com.biblioteko.biblioteko.book.Book;
import com.biblioteko.biblioteko.studentClass.StudentClass;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private StudentClass student;

    @Column(nullable = false)
    private String comment;

    public void setBook(Book book2) {
    }

    public void setStudent(StudentClass student2) {
    }

    public void setComment(String comment2) {
    }

    // Adicionar construtores
}