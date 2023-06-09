package com.biblioteko.biblioteko.studentClass;

import java.util.Set;
import java.util.UUID;

import com.biblioteko.biblioteko.book.Book;
import com.biblioteko.biblioteko.user.User;

import java.util.Set;
import java.util.UUID;
import com.biblioteko.biblioteko.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "studentClass")
@Getter
@Setter
public class StudentClass {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false)
    private int classYear;

	@Column(nullable = false)
	private String schoolSubject;

	@Column(nullable = true)
	private String photo;

	@ManyToOne
    @JoinColumn(name = "owner_id")
	private User owner;

	@ManyToMany
    @JoinTable(
        name = "class_students",
        joinColumns = @JoinColumn(name = "studentClass_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
	private Set<User> students;
	
	@ManyToMany
    @JoinTable(
        name = "class_suggested_books",
        joinColumns = @JoinColumn(name = "studentClass_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id")
    )
	private Set<Book> suggestedBooks;

	public void removeStudents(Set<UUID> studentIdsToRemove) {
		if (students == null || studentIdsToRemove == null || studentIdsToRemove.isEmpty()) {
			return;
		}
		
		students.removeIf(student -> studentIdsToRemove.contains(student.getId()));

	}
	
	public boolean addSuggestedBook(Book book) {
		
		return this.suggestedBooks.add(book);
		
	}
	
	public boolean removeSuggestedBook(Book book) {
		
		return this.suggestedBooks.remove(book);
		
	}
	
	public boolean bookAlreadySuggested(Book book) {
		return this.suggestedBooks.contains(book);
	}
	
	public boolean addMember(User member) {
		return this.students.add(member);
	}
	
	public boolean removeMember(User member) {
		return this.students.remove(member);
	}

}
