package com.biblioteko.biblioteko.studentClass;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteko.biblioteko.book.Book;
import com.biblioteko.biblioteko.book.BookDTO;
import com.biblioteko.biblioteko.book.BookRepository;
import com.biblioteko.biblioteko.book.BookService;
import com.biblioteko.biblioteko.exception.BookAlreadySuggestedException;
import com.biblioteko.biblioteko.exception.BookNotFoundException;
import com.biblioteko.biblioteko.exception.NoClassesFoundException;
import com.biblioteko.biblioteko.exception.StudentClassNotFoundException;
import com.biblioteko.biblioteko.exception.UserAlreadyAMemberOfClassException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;
import com.biblioteko.biblioteko.exception.UserUnauthorizedException;
import com.biblioteko.biblioteko.user.User;
import com.biblioteko.biblioteko.user.UserDTO;
import com.biblioteko.biblioteko.user.UserService;
import com.biblioteko.biblioteko.utils.BookMapper;
import com.biblioteko.biblioteko.utils.UserMapper;

@Service
public class StudentClassService {

    @Autowired
    private StudentClassRepository studentClassRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;
    
    private BookMapper mapper = new BookMapper();

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
    	
    	User owner = studentClass.getOwner();
    	
    	UserDTO ownerDto = new UserDTO(
    			           owner.getId(),
    			           owner.getName(),
    			           owner.getEmail(),
    			           owner.getRole(),
    			           owner.getReadingList().stream()
    			                                 .map(l -> mapper.convertToBookDTO(l.getBook()))
    			                                 .collect(Collectors.toSet()),
    			           owner.getStarredBooks().stream()
    			                                  .map(b -> mapper.convertToBookDTO(b))
    			                                  .collect(Collectors.toSet())
    			);
    	
    	StudentClassDTO prev = new StudentClassDTO(studentClass.getId(),
                studentClass.getName(),
                studentClass.getClassYear(),
                studentClass.getSchoolSubject(),
                studentClass.getPhoto(),
                ownerDto,
                null,
                null);
    	
        if (studentClass.getStudents() != null) {
            
        	prev.setStudents(studentClass.getStudents()
                            .stream()
                            .map(u -> u.getId())
                            .collect(Collectors.toSet()));
        }
        
        if(studentClass.getSuggestedBooks() != null) {
        	
        	prev.setSuggestedBooks(studentClass.getSuggestedBooks()
                            .stream()
                            .map(u -> u.getId())
                            .collect(Collectors.toSet()));
        	
        }
        
        return prev;

        
    }

    public List<UserDTO> getStudentsOfClass(UUID classId) throws StudentClassNotFoundException {
        StudentClass studentClass = findById(classId);

        List<UserDTO> studentsOfClass = studentClass.getStudents()
                .stream()
                .map(u -> UserMapper.convertToUserDTO(u))
                .collect(Collectors.toList());
        return studentsOfClass;
    }

    public void removeStudentClass(UUID classId, UUID userId)
            throws StudentClassNotFoundException, UserUnauthorizedException, UserNotFoundException {
      
        User user = userService.findUserById(userId);

        StudentClass studentClass = findById(classId);

        if (!studentClass.getOwner().getId().equals(user.getId())) {
            throw new UserUnauthorizedException("Não possui autorização para realizar essa alteração!");
        }

        studentClassRepository.delete(studentClass);
    }

    public StudentClassDTO getStudentClassDetails(UUID classId) throws StudentClassNotFoundException {
        StudentClass studentClass = findById(classId);

        return convertToStudentClassDTO(studentClass);
    }

    public void editStudentClass(UUID classId, UUID userId, StudentClassDTO studentClassDTO)
            throws StudentClassNotFoundException, UserNotFoundException, UserUnauthorizedException {
        User user = userService.findUserById(userId);

        StudentClass studentClass = findById(classId);

        if (!studentClass.getOwner().getId().equals(user.getId())) {
          
            throw new UserUnauthorizedException("Não possui autorização para realizar essa alteração!");
        }

        studentClass.setName(studentClassDTO.getName());
        studentClass.setClassYear(studentClassDTO.getClassYear());
        studentClass.setSchoolSubject(studentClassDTO.getSchoolSubject());
        studentClass.setPhoto(studentClassDTO.getPhoto());

        Set<UUID> studentsToRemove = studentClassDTO.getStudents();
        studentClass.removeStudents(studentsToRemove);

        studentClassRepository.save(studentClass);
    }

    public StudentClass findById(UUID studentClassId) throws StudentClassNotFoundException {
        Optional<StudentClass> studentClass = this.studentClassRepository.findById(studentClassId);
        if (!studentClass.isPresent())
            throw new StudentClassNotFoundException("Turma não encontrada.");
      
        return studentClass.get();
    }
    
    public BookDTO suggestBook(UUID userId, UUID bookId, UUID studentClassId) throws BookNotFoundException, StudentClassNotFoundException,
    UserUnauthorizedException, BookAlreadySuggestedException, UserNotFoundException {
    	
    	userService.findUserById(userId);
    	
    	if(!studentClassRepository.existsById(studentClassId)) throw new StudentClassNotFoundException("Turma não encontrada!");
    	StudentClass studentClass = studentClassRepository.findById(studentClassId).get();
    	
    	if(!studentClass.getOwner().getId().equals(userId)) throw new UserUnauthorizedException("Você não tem permissão para realizar esta ação.");
    	
    	if(!bookRepository.existsById(bookId)) throw new BookNotFoundException("Livro não encontrado!");
    	Book book = bookRepository.findById(bookId).get();
    	
    	if(studentClass.bookAlreadySuggested(book)) throw new BookAlreadySuggestedException("Este livro já foi sugerido a esta turma.");
    	
    	studentClass.addSuggestedBook(book);
    	studentClassRepository.save(studentClass);
    	
    	return mapper.convertToBookDTO(book);
    	
    }
    
    public List<BookDTO> getClassProgress(){
    	//TODO
    	return null;
    	
    }
    
    public Set<StudentClassDTO> getClasses(UUID userId) throws UserNotFoundException, NoClassesFoundException {
    	
    	if(!userService.existsById(userId)) throw new UserNotFoundException("Usuário não encontrado.");
    	
    	User user = userService.findUserById(userId);
    	
    	Set<StudentClass> list;
    	
    	Set<StudentClassDTO> res;
    	
    	if(user.getRole().equals("PROFESSOR")) {
    	
    		list = studentClassRepository.findByOwnerId(userId);

    		if(list.isEmpty()) throw new NoClassesFoundException("Você não é administrador de nenhuma turma.");

    		res = list.stream()
    				.map(c -> convertToStudentClassDTO(c))
    				.collect(Collectors.toSet());
    	}else if(user.getRole().equals("ALUNO")) {
    		
    		list = studentClassRepository.getClassesContainingUser(userId);
        	
        	if(list.isEmpty()) throw new NoClassesFoundException("Você não faz parte de nenhuma turma.");
        	
        	res = list.stream()
                    .map(c -> convertToStudentClassDTO(c))
                    .collect(Collectors.toSet());
    	}else {
    		list = null;
    		res = null;
    	}
    	
    	return res;
    }
    
    public void joinClass(UUID userId, UUID classId) throws UserNotFoundException, StudentClassNotFoundException, UserAlreadyAMemberOfClassException {
    	
    	User user = userService.findUserById(userId);
    	
    	StudentClass studentClass = this.findById(classId);
    	
    	if(!studentClass.addMember(user)) throw new UserAlreadyAMemberOfClassException("Você já faz parte desta turma.");
    	
    	studentClassRepository.save(studentClass);
    	
    }
    

}
