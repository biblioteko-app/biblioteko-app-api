package com.biblioteko.biblioteko.user;
import java.util.Set;
import java.util.UUID;
import com.biblioteko.biblioteko.read.Read;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    private Set<Read> redingList;
    
    public User(String name, String email, String password, String role) {
    	this.name = name;
    	this.email = email;
    	this.password = password;
    	this.role = role;
    }

    public void addRead(Read read){
        if(!redingList.contains(read)){
            redingList.add(read);
        }
    }
}

