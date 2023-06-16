package com.biblioteko.biblioteko.studentClass;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentClassRepository extends JpaRepository<StudentClass, UUID> {
	
    public Optional<StudentClass> findById(UUID classId);
    
    public Set<StudentClass> findByOwnerId(UUID ownerId);
    
    @Query(value="SELECT c.* FROM STUDENT_CLASS c, CLASS_STUDENTS cc WHERE c.ID = cc.STUDENT_CLASS_ID AND cc.USER_ID = ?1", nativeQuery = true)
    public Set<StudentClass> getClassesContainingUser(UUID userId);
    
}
