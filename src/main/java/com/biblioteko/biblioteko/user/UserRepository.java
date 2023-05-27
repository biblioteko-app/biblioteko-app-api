package com.biblioteko.biblioteko.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
	
    Optional<User> findById(UUID id);
    
    List<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    boolean existsByName(String name);
}

