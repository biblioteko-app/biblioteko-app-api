package com.biblioteko.biblioteko.user;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.biblioteko.biblioteko.response.MessageResponse;
import com.biblioteko.biblioteko.roles.RoleRepository;
import com.biblioteko.biblioteko.security.jwt.JwtUtils;
import com.biblioteko.biblioteko.security.services.AuthUserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthUserService authUserService;
    
    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

   @PostMapping("/signup")
   public ResponseEntity<?> createUser(@Valid @RequestBody NewUserDTO newUserDTO) {
	  
	  try {
		  newUserDTO.setPassword(encoder.encode(newUserDTO.getPassword()));
		  UserDTO userDTO = userService.createUser(newUserDTO);
	      return ResponseEntity.created(URI.create("/users" + userDTO.getId())).body(userDTO);
	  }catch(IllegalArgumentException e) {
		  return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	  }catch(EmailAlreadyExistsException e) {
		  return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	  }catch(Exception e) {
		  return new ResponseEntity<String>("Erro ao criar usuario.", HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	  
   }
   
   @GetMapping("/{user_id}")
   public ResponseEntity<?> getUserDetails(@PathVariable("user_id") UUID userId){
	   try {
		   UserDTO userDTO = userService.getUserDetails(userId);

		   return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	   }catch(UserNotFoundException e) {
		   return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	   }catch(Exception e) {
		   return new ResponseEntity<>("Nao foi possivel visualizar os detalhes do usuario.", HttpStatus.INTERNAL_SERVER_ERROR);
	   }
	   
   }
   
   @PutMapping("/{user_id}")
   @PreAuthorize("@authUserService.checkId(#userId)")
   public ResponseEntity<?> editUserDetails(@PathVariable("user_id") UUID userId, @RequestBody @Valid NewUserDTO newUserDTO){
	   try {
		   newUserDTO.setPassword(encoder.encode(newUserDTO.getPassword()));
		   userService.editUserDetails(userId, newUserDTO);
		   ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		   return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
			        .body(new MessageResponse("Usuario atualizado com sucesso."));
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
   
   @DeleteMapping("/{user_id}")
   @PreAuthorize("@authUserService.checkId(#userId)")
   public ResponseEntity<?> deleteUser(@PathVariable("user_id") UUID userId){
	   
	   try {
		   userService.deleteUser(userId);
		   ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		   return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
			        .body(new MessageResponse("Usuario removido com sucesso."));
	   }catch(UserNotFoundException e) {
		   return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	   }catch(Exception e) {
		   return new ResponseEntity<>("Nao foi possivel remover o usuario.", HttpStatus.INTERNAL_SERVER_ERROR);
	   }
	   
   }
}
