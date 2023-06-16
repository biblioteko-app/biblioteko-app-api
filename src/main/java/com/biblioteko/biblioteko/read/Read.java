package com.biblioteko.biblioteko.read;
import java.util.UUID;
import com.biblioteko.biblioteko.book.Book;
import com.biblioteko.biblioteko.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "read")
@Getter
@Setter
@NoArgsConstructor
public class Read {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    
    @Column(nullable = false)
    private Integer readPages;

    public Read(User user, Book book, Integer readPages) {
        this.user = user;
        this.book = book;
        this.readPages = readPages;
    }
    
    public boolean isFinalized() {
    	return this.readPages == this.book.getPages();
    }
    
    public Float getProgress() {
    	return (Float) (this.readPages / (float) this.book.getPages());
    }
}
