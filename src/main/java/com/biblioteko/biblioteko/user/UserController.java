package com.biblioteko.biblioteko.user;
import java.util.UUID;

import java.net.URI;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.biblioteko.biblioteko.response.UserInfoResponse;
import com.biblioteko.biblioteko.roles.RoleRepository;
import com.biblioteko.biblioteko.security.jwt.JwtUtils;
import com.biblioteko.biblioteko.security.services.AuthUserService;
import com.biblioteko.biblioteko.security.services.UserDetailsImpl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.biblioteko.biblioteko.exception.EmailAlreadyExistsException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;

@Controller
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthUserService authUserService;
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    RoleRepository roleRepository;

   @PostMapping("/signup")
   public ResponseEntity<?> createUser(@Valid @RequestBody NewUserDTO newUserDTO) {
	  
	  try {
		  UserDTO userDTO = userService.createUser(newUserDTO);
	      return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
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
   public ResponseEntity<?> editUserDetails(@PathVariable("user_id") UUID userId, @RequestBody @Valid UserDTO userDTO){
	   try {
		   
		   userService.editUserDetails(userId, userDTO);

		   UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   
		   userDetails.setEmail(userDTO.getEmail());

		   ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

		   List<String> roles = userDetails.getAuthorities().stream()
				   .map(item -> item.getAuthority())
				   .collect(Collectors.toList());

		   return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
				   .body(new UserInfoResponse(userId,
						   userDTO.getEmail(),
						   roles));
		   
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
   
   @PutMapping("/{user_id}/changepass")
   @PreAuthorize("@authUserService.checkId(#userId)")
   public ResponseEntity<?> changeUserPassword(@PathVariable("user_id") UUID userId, @RequestBody String newPass){
	   try {
		   
		 userService.changePassword(userId, newPass);
		 ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		 return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
			        .body(new MessageResponse("Senha alterada. Refaca login."));
		 
	   }catch(IllegalArgumentException e) {
		   
		   return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		   
	   }catch(UserNotFoundException e) {
		   
		   return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		   
	   }catch(Exception e) {
		   return new ResponseEntity<>("Nao foi possivel alterar a senha.", HttpStatus.INTERNAL_SERVER_ERROR);
	   }
   }
   
   @DeleteMapping("/{user_id}")
   @PreAuthorize("@authUserService.checkId(#userId)")
   public ResponseEntity<?> deleteUser(@PathVariable("user_id") UUID userId) {
	   
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
