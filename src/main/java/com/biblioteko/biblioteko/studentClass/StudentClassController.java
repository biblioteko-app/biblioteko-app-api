package com.biblioteko.biblioteko.studentClass;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.biblioteko.biblioteko.exception.StudentClassNotFoundException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;
import com.biblioteko.biblioteko.exception.UserUnauthorized;
import com.biblioteko.biblioteko.user.UserDTO;

@Controller
@RequestMapping("/studentclass")
public class StudentClassController {

    @Autowired
    StudentClassService studentClassService;

    @PostMapping("/{user_id}")
    public ResponseEntity<?> createStudentClass(@RequestBody NewStudentClassDTO newStudentClassDTO, @PathVariable("user_id") UUID userId) {
        try{   
            StudentClassDTO studentClassDTO = studentClassService.createStudentClass(newStudentClassDTO, userId);
            return new ResponseEntity<>(studentClassDTO.getId(), HttpStatus.CREATED);
        }catch(UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
            List<UserDTO> students = studentClassService.getStudentsOfClass(classId);

            if(students.isEmpty()){
                return new ResponseEntity<>("A turma não possui alunos matriculados!", HttpStatus.OK);
            }else{
                return new ResponseEntity<>(students, HttpStatus.OK);
            }
        } catch (StudentClassNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao obter os alunos da turma.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{user_id}/{class_id}")
    public ResponseEntity<?> removeStudentClass(@PathVariable("class_id") UUID classId, @PathVariable("user_id") UUID userId) {
        try {
            studentClassService.removeStudentClass(classId, userId);
            return new ResponseEntity<>("Turma removida com sucesso!", HttpStatus.OK);
        } catch (StudentClassNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao remover a turma.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{class_id}")
    public ResponseEntity<?> getStudentClassDetails(@PathVariable("class_id") UUID classId) {
        try {
            StudentClassDTO studentClassDTO = studentClassService.getStudentClassDetails(classId);
            return new ResponseEntity<>(studentClassDTO, HttpStatus.OK);
        } catch (StudentClassNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao obter detalhes da turma.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{user_id}/{class_id}")
    public ResponseEntity<?> editClass(@PathVariable("class_id") UUID classId, @RequestBody StudentClassDTO studentClassDTO, @PathVariable("user_id") UUID userId) {
        try {
            studentClassService.editStudentClass(classId, userId, studentClassDTO);
            return new ResponseEntity<>("Turma atualizada com sucesso!", HttpStatus.OK);
        } catch (StudentClassNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (UserUnauthorized e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao editar a turma.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
