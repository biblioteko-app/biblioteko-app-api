package com.biblioteko.biblioteko.book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    public Optional<Book> findById(UUID bookId);
    public List<Book> findByOwnerId(UUID ownerId);
}
