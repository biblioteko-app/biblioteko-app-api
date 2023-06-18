package com.biblioteko.biblioteko.studentClass;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.biblioteko.biblioteko.book.BookDTO;
import com.biblioteko.biblioteko.exception.BookAlreadySuggestedException;
import com.biblioteko.biblioteko.exception.BookNotFoundException;
import com.biblioteko.biblioteko.exception.NoBooksFoundException;
import com.biblioteko.biblioteko.exception.NoClassesFoundException;
import com.biblioteko.biblioteko.exception.NotASuggestedBookException;
import com.biblioteko.biblioteko.exception.StudentClassNotFoundException;
import com.biblioteko.biblioteko.exception.UnauthenticatedUserException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;
import com.biblioteko.biblioteko.exception.UserUnauthorizedException;
import com.biblioteko.biblioteko.security.services.AuthUserService;
import com.biblioteko.biblioteko.user.UserDTO;

@RestController
@RequestMapping("/api/studentclass")
public class StudentClassController {

	@Autowired
	StudentClassService studentClassService;

	@Autowired
	private AuthUserService authUserService;

	@PostMapping("/{user_id}")
	@PreAuthorize("@authUserService.checkId(#userId) and @authUserService.isProf()")
	public ResponseEntity<?> createStudentClass(@RequestBody NewStudentClassDTO newStudentClassDTO, @PathVariable("user_id") UUID userId) {
		try{   
			StudentClassDTO studentClassDTO = studentClassService.createStudentClass(newStudentClassDTO, userId);
			return new ResponseEntity<>(studentClassDTO.getId(), HttpStatus.CREATED);
		}catch(UserNotFoundException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		catch(IllegalArgumentException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}catch(Exception e){
			return new ResponseEntity<String>("Erro ao criar a turma.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{class_id}/students")
	public ResponseEntity<?> getStudentsOfClass(@PathVariable("class_id") UUID classId) {
		try {
			UUID userId = authUserService.getCurrentUser().getId();
			List<UserDTO> students = studentClassService.getStudentsOfClass(userId, classId);

			if(students.isEmpty()){
				return new ResponseEntity<>("A turma não possui alunos matriculados!", HttpStatus.OK);
			}else{
				return new ResponseEntity<>(students, HttpStatus.OK);
			}
		} catch (StudentClassNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch (UnauthenticatedUserException | UserUnauthorizedException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao obter os alunos da turma.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}



	@DeleteMapping("/{class_id}")
	@PreAuthorize("@authUserService.isProf()")
	public ResponseEntity<?> removeStudentClass(@PathVariable("class_id") UUID classId) {
		try {
			UUID userId = authUserService.getCurrentUser().getId();
			studentClassService.removeStudentClass(classId, userId);
			return new ResponseEntity<>("Turma removida com sucesso!", HttpStatus.OK);
		} catch (StudentClassNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch (UnauthenticatedUserException | UserUnauthorizedException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao remover a turma.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{class_id}")
	public ResponseEntity<?> getStudentClassDetails(@PathVariable("class_id") UUID classId) {
		try {
			
			UUID queryAuthorId = authUserService.getCurrentUser().getId();
			StudentClassDTO studentClassDTO = studentClassService.getStudentClassDetails(queryAuthorId, classId);
			return new ResponseEntity<>(studentClassDTO, HttpStatus.OK);
		} catch (UserNotFoundException | StudentClassNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch (UnauthenticatedUserException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao obter detalhes da turma.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{class_id}")
	@PreAuthorize("@authUserService.isProf()")
	public ResponseEntity<?> editClass(@PathVariable("class_id") UUID classId, @RequestBody StudentClassDTO studentClassDTO) {
		try {
			UUID userId = authUserService.getCurrentUser().getId();
			studentClassService.editStudentClass(classId, userId, studentClassDTO);
			return new ResponseEntity<>("Turma atualizada com sucesso!", HttpStatus.OK);
		} catch (StudentClassNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch (UnauthenticatedUserException | UserUnauthorizedException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Erro ao editar a turma.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{class_id}/{book_id}")
	@PreAuthorize("@authUserService.isProf()")
	public ResponseEntity<?> suggestBook(@PathVariable("class_id") UUID classId, @PathVariable("book_id") UUID bookId){

		try {
			UUID userId = authUserService.getCurrentUser().getId();
			studentClassService.suggestBook(userId, bookId, classId);
			return new ResponseEntity<>("Livro sugerido com sucesso!", HttpStatus.OK);
		}catch(UserNotFoundException | BookNotFoundException | StudentClassNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch(UnauthenticatedUserException | UserUnauthorizedException | BookAlreadySuggestedException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<>("Erro ao sugerir livro.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


	@GetMapping("/{class_id}/progress")
	public ResponseEntity<?> getClassProgress(@PathVariable("class_id") UUID classId){

		try {
			UUID userId = authUserService.getCurrentUser().getId();
			ClassProgressDTO classProgressDTO = studentClassService.getClassProgress(userId, classId);
			return new ResponseEntity<>(classProgressDTO, HttpStatus.OK);
		}catch(UserNotFoundException | StudentClassNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch(UserUnauthorizedException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<>("Erro ao obter progresso da turma.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/{class_id}/books")
	public ResponseEntity<?> getSuggestedBooks(@PathVariable("class_id") UUID classId){

		try {
			UUID userId = authUserService.getCurrentUser().getId();
			Set<BookDTO> books = studentClassService.getSuggestedBooks(userId, classId);
			return new ResponseEntity<>(books, HttpStatus.OK);
		}catch(UserNotFoundException | StudentClassNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch(UnauthenticatedUserException | UserUnauthorizedException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}catch(NoBooksFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>("Erro ao obter livros sugeridos à turma.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/{class_id}/unsuggestBook")
	@PreAuthorize("@authUserService.isProf()")
	public ResponseEntity<?> unsuggestBook(@PathVariable("class_id") UUID classId, @RequestParam("id") UUID bookId){
		try {
			UUID userId = authUserService.getCurrentUser().getId();
			studentClassService.unsuggestBook(userId, classId, bookId);
			return new ResponseEntity<>("Sugestão de livro removida.", HttpStatus.OK);
		}catch(UserNotFoundException | StudentClassNotFoundException | BookNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch(UnauthenticatedUserException | UserUnauthorizedException | NotASuggestedBookException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<>("Erro ao desfazer sugestão de livro.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
