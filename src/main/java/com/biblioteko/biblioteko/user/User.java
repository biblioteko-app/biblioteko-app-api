package com.biblioteko.biblioteko.user;

import java.util.Set;
import java.util.UUID;

import com.biblioteko.biblioteko.book.Book;
import com.biblioteko.biblioteko.read.Read;
import com.biblioteko.biblioteko.security.roles.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String role;

    @OneToMany(mappedBy = "user")
    private Set<Read> readingList;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", 
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> securityRoles = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_starred_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> starredBooks = new HashSet<>();
    
    public User(String name, String email, String password, String role) {
    	this.name = name;
    	this.email = email;
    	this.password = password;
    	this.role = role;
    }

    public void addRead(Read read){
        if(!readingList.contains(read)){
            readingList.add(read);
        }
    }

    public void addFavoriteBook(Book book) {
        this.starredBooks.add(book);
    }
}

