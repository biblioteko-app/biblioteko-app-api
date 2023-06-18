package com.biblioteko.biblioteko.user;
import java.util.UUID;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.biblioteko.biblioteko.book.BookDTO;
import com.biblioteko.biblioteko.exception.EmailAlreadyExistsException;
import com.biblioteko.biblioteko.exception.NoClassesFoundException;
import com.biblioteko.biblioteko.exception.StudentClassNotFoundException;
import com.biblioteko.biblioteko.exception.UserAlreadyAMemberOfClassException;
import com.biblioteko.biblioteko.exception.UserAlreadyLoggedInException;
import com.biblioteko.biblioteko.exception.UserNotAMemberOfClassException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;
import com.biblioteko.biblioteko.security.jwt.JwtUtils;
import com.biblioteko.biblioteko.security.response.MessageResponse;
import com.biblioteko.biblioteko.security.response.UserInfoResponse;
import com.biblioteko.biblioteko.security.roles.RoleRepository;
import com.biblioteko.biblioteko.security.services.AuthUserService;
import com.biblioteko.biblioteko.security.services.UserDetailsImpl;
import com.biblioteko.biblioteko.studentClass.StudentClassDTO;
import com.biblioteko.biblioteko.studentClass.StudentClassService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.biblioteko.biblioteko.exception.EmailAlreadyExistsException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private StudentClassService studentClassService;
    
    @SuppressWarnings("unused")
	@Autowired
    private AuthUserService authUserService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private RoleRepository roleRepository;

   @PostMapping("/signup")
   public ResponseEntity<?> createUser(@Valid @RequestBody NewUserDTO newUserDTO) {
	  
	  try {
		  UserDTO userDTO = userService.createUser(newUserDTO);
	      return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
	  }catch(EmailAlreadyExistsException | IllegalArgumentException | UserAlreadyLoggedInException e) {
		  return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	  }catch(Exception e) {
		  return new ResponseEntity<String>("Erro ao criar usuario: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
						   roles, jwtCookie.toString()));
		   
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
   
   @GetMapping("/{user_id}/classes")
   @PreAuthorize("@authUserService.checkId(#userId)")
   public ResponseEntity<?> getClasses(@PathVariable("user_id") UUID userId) {
	   try {
		   Set<StudentClassDTO> classes = studentClassService.getClasses(userId);
		   return new ResponseEntity<Set<StudentClassDTO>>(classes, HttpStatus.OK);
	   }catch(UserNotFoundException e) {
		   return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	   }catch(NoClassesFoundException e) {
		   return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
	   }catch(Exception e) {
		   return new ResponseEntity<>("Erro ao listar turmas.", HttpStatus.INTERNAL_SERVER_ERROR);
	   }
   }
   
   @PostMapping("/{user_id}/joinclass")
   @PreAuthorize("@authUserService.checkId(#userId)")
   public ResponseEntity<?> joinClass(@PathVariable("user_id") UUID userId, @RequestParam("classId") UUID classId){
	   try {
		   studentClassService.joinClass(userId, classId);
		   return new ResponseEntity<>("Você agora faz parte da turma.", HttpStatus.OK);
	   }catch(UserNotFoundException | StudentClassNotFoundException e) {
		   return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	   }catch(UserAlreadyAMemberOfClassException e) {
		   return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
	   }catch(Exception e) {
		   return new ResponseEntity<>("Erro ao entrar na turma.", HttpStatus.INTERNAL_SERVER_ERROR);
	   }

   }
   
   @PostMapping("/{user_id}/leaveclass")
   @PreAuthorize("@authUserService.checkId(#userId)")
   public ResponseEntity<?> leaveClass(@PathVariable("user_id") UUID userId, @RequestParam("classId") UUID classId){
	   try {
		   studentClassService.leaveClass(userId, classId);
		   return new ResponseEntity<>("Você não mais faz parte da turma.", HttpStatus.OK);
	   }catch(UserNotFoundException | StudentClassNotFoundException e) {
		   return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	   }catch(UserNotAMemberOfClassException e) {
		   return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
	   }catch(Exception e) {
		   return new ResponseEntity<>("Erro ao deixar a turma.", HttpStatus.INTERNAL_SERVER_ERROR);
	   }

   }

   @GetMapping("/{user_id}/favorite-books")
   @PreAuthorize("@authUserService.checkId(#userId)")
   public ResponseEntity<?> getFavoriteBooks(@PathVariable("user_id") UUID userId){
	   try {
		   Set<BookDTO> starredBooks = userService.getFavoriteBooks(userId);

		   return new ResponseEntity<>(starredBooks, HttpStatus.OK);
		   
	   }catch(UserNotFoundException e) {
		   return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		   
	   }catch(Exception e) {
		   return new ResponseEntity<>("Nao foi possivel pegar os livros favoritados.", HttpStatus.INTERNAL_SERVER_ERROR);
	   }  
   }


   @GetMapping("/{user_id}/finished-books")
   @PreAuthorize("@authUserService.checkId(#userId)")
   public ResponseEntity<?> getFinishedBooks(@PathVariable("user_id") UUID userId){
	   try {
		   Set<BookDTO> finishedBooks = userService.getFinishedBooks(userId);

		   return new ResponseEntity<>(finishedBooks, HttpStatus.OK);
		   
	   }catch(UserNotFoundException e) {
		   return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		   
	   }catch(Exception e) {
		   return new ResponseEntity<>("Nao foi possivel pegar os livros finalizados.", HttpStatus.INTERNAL_SERVER_ERROR);
	   }  
   }
   
   
}
