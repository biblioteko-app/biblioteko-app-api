package com.biblioteko.biblioteko.book;
import java.util.UUID;

import com.biblioteko.biblioteko.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "book")
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, unique = true, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = true)
    private String gender;

    @Column(nullable = false)
    private Integer edition;

    @Column(nullable = true)
    private String synopsis;
    
    @Column(nullable = false)
    private Float rating;

    @Column(nullable = true)
    private String photo;

    @Column(nullable = false)
    private Integer pages;

    @Column(nullable = false)
    private String accessLink;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public Book(String title, String author, String gender, Integer edition, String synopsis, Float rating, String photo, Integer pages, String accessLink, User owner) {
        this.title = title;
        this.author = author;
        this.gender = gender;
        this.edition = edition;
        this.synopsis = synopsis;
        this.rating = rating;
        this.photo = photo;
        this.pages = pages;
        this.accessLink = accessLink;
        this.owner = owner;
    }

}
