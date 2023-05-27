package com.biblioteko.biblioteko.studentClass;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentClassRepository extends JpaRepository<StudentClass, UUID>{
    public Optional<StudentClass> findById(UUID classId);
    public void delete(StudentClass studentClass);    
}
