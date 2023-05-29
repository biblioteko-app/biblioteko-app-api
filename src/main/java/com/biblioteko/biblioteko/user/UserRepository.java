package com.biblioteko.biblioteko.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    public Optional<User> findById(UUID classId);
    User findByName(String name);
}

