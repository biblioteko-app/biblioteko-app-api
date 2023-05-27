package com.biblioteko.biblioteko.user;

import java.net.URI;
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

import com.biblioteko.biblioteko.exception.EmailAlreadyExistsException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;

@Controller
@RequestMapping("/users/")
public class UserController {

    @Autowired
    private UserService userService;

   @PostMapping
   public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
	  
	  try {
		  User user = userService.createUser(userDTO);
	      return ResponseEntity.created(URI.create("/users" + user.getId())).body(userDTO);
	  }catch(IllegalArgumentException e) {
		  return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	  }catch(EmailAlreadyExistsException e) {
		  return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	  }catch(Exception e) {
		  return new ResponseEntity<String>("Erro ao criar usuario.", HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	  
   }
   
   @GetMapping("/{user_id}/")
   public ResponseEntity<?> getUserDetails(@PathVariable("user_id") UUID userId){
	   
	   try {
		   UserDTO userDTO = userService.getUserDetails(userId);
		   return new ResponseEntity<UserDTO>(userDTO, HttpStatus.BAD_REQUEST);
	   }catch(UserNotFoundException e) {
		   return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	   }catch(Exception e) {
		   return new ResponseEntity<>("Nao foi possivel visualizar os detalhes do usuario.", HttpStatus.INTERNAL_SERVER_ERROR);
	   }
	   
   }
   
   @PutMapping("/{user_id}/")
   public ResponseEntity<?> editUserDetails(@PathVariable("user_id") UUID userId, @RequestBody UserDTO userDTO){
	   try {
		   userService.editUserDetails(userId, userDTO);
		   return new ResponseEntity<>("Usuario atualizado com sucesso!", HttpStatus.OK);
	   }catch(IllegalArgumentException e) {
		   return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	   }catch(UserNotFoundException e) {
		   return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	   }catch(EmailAlreadyExistsException e) {
		   return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	   }catch(Exception e) {
		   return new ResponseEntity<>("Nao foi possivel alterar os detalhes do usuario.", HttpStatus.INTERNAL_SERVER_ERROR);
	   }
   }
   
   @DeleteMapping("/{user_id}/")
   public ResponseEntity<?> deleteUser(@PathVariable("user_id") UUID userId){
	   
	   try {
		   userService.deleteUser(userId);
		   return new ResponseEntity<>("Usuario removido com sucesso.", HttpStatus.OK);
	   }catch(UserNotFoundException e) {
		   return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	   }catch(Exception e) {
		   return new ResponseEntity<>("Nao foi possivel remover o usuario.", HttpStatus.INTERNAL_SERVER_ERROR);
	   }
	   
   }
}
