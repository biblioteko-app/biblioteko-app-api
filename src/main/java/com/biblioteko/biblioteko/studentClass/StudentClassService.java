package com.biblioteko.biblioteko.studentClass;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteko.biblioteko.exception.StudentClassNotFoundException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;
import com.biblioteko.biblioteko.exception.UserUnauthorized;
import com.biblioteko.biblioteko.user.User;
import com.biblioteko.biblioteko.user.UserDTO;
import com.biblioteko.biblioteko.user.UserService;

@Service
public class StudentClassService {

    @Autowired
    private StudentClassRepository studentClassRepository;

    @Autowired
    private UserService userService;

    public StudentClassDTO createStudentClass(NewStudentClassDTO newStudentClassDTO, UUID userId)
            throws UserNotFoundException {
        User user = userService.findUserById(userId);

        if (newStudentClassDTO.getName() == null || newStudentClassDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Nome da turma inválido.");
        }
        if (newStudentClassDTO.getClassYear() == 0) {
            throw new IllegalArgumentException("Ano letivo inválido.");
        }

        if (newStudentClassDTO.getSchoolSubject() == null || newStudentClassDTO.getSchoolSubject().isEmpty()) {
            throw new IllegalArgumentException("Disciplina inválida.");
        }

        StudentClass studentClass = new StudentClass();
        studentClass.setName(newStudentClassDTO.getName());
        studentClass.setClassYear(newStudentClassDTO.getClassYear());
        studentClass.setSchoolSubject(newStudentClassDTO.getSchoolSubject());
        studentClass.setPhoto(newStudentClassDTO.getPhoto());
        studentClass.setOwner(user);
        return convertToStudentClassDTO(studentClassRepository.save(studentClass));
    }

    public StudentClassDTO convertToStudentClassDTO(StudentClass studentClass) {
        if (studentClass.getStudents() == null) {
            return new StudentClassDTO(studentClass.getId(),
                    studentClass.getName(),
                    studentClass.getClassYear(),
                    studentClass.getSchoolSubject(),
                    studentClass.getPhoto(),
                    studentClass.getOwner(),
                    null);

        } else {
            return new StudentClassDTO(studentClass.getId(),
                    studentClass.getName(),
                    studentClass.getClassYear(),
                    studentClass.getSchoolSubject(),
                    studentClass.getPhoto(),
                    studentClass.getOwner(),
                    studentClass.getStudents()
                            .stream()
                            .map(u -> u.getId())
                            .collect(Collectors.toSet()));
        }
    }

    public List<UserDTO> getStudentsOfClass(UUID classId) throws StudentClassNotFoundException {
        StudentClass studentClass = findStudentClassById(classId);

        List<UserDTO> studentsOfClass = studentClass.getStudents()
                .stream()
                .map(u -> userService.convertToUserDTO(u))
                .collect(Collectors.toList());
        return studentsOfClass;
    }

    public void removeStudentClass(UUID classId, UUID userId)
            throws StudentClassNotFoundException, UserUnauthorized, UserNotFoundException {
        User user = userService.findUserById(userId);

        StudentClass studentClass = findStudentClassById(classId);

        if (!studentClass.getOwner().getId().equals(user.getId())) {
            throw new UserUnauthorized("Não possui autorização para realizar essa alteração!");
        }

        studentClassRepository.delete(studentClass);
    }

    public StudentClassDTO getStudentClassDetails(UUID classId) throws StudentClassNotFoundException {
        StudentClass studentClass = findStudentClassById(classId);

        return convertToStudentClassDTO(studentClass);
    }

    public void editStudentClass(UUID classId, UUID userId, StudentClassDTO studentClassDTO)
            throws StudentClassNotFoundException, UserNotFoundException, UserUnauthorized {
        User user = userService.findUserById(userId);

        StudentClass studentClass = findStudentClassById(classId);

        if (!studentClass.getOwner().getId().equals(user.getId())) {
            throw new UserUnauthorized("Não possui autorização para realizar essa alteração!");
        }

        studentClass.setName(studentClassDTO.getName());
        studentClass.setClassYear(studentClassDTO.getClassYear());
        studentClass.setSchoolSubject(studentClassDTO.getSchoolSubject());
        studentClass.setPhoto(studentClassDTO.getPhoto());

        Set<UUID> studentsToRemove = studentClassDTO.getStudents();
        studentClass.removeStudents(studentsToRemove);

        studentClassRepository.save(studentClass);
    }

    public StudentClass findStudentClassById(UUID studentClassId) throws StudentClassNotFoundException {
        Optional<StudentClass> studentClass = this.studentClassRepository.findById(studentClassId);
        if (!studentClass.isPresent())
            throw new StudentClassNotFoundException("Turma não encontrada.");

        return studentClass.get();
    }

    public static StudentClass findById(Long studentId) {
        return null;
    }

}
