package com.biblioteko.biblioteko.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
	
    Optional<User> findById(UUID id);
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    boolean existsById(UUID id);
    
    boolean existsByName(String name);
  
    User findByName(String name);
}

