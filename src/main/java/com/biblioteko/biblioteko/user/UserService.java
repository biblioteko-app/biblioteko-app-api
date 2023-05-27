package com.biblioteko.biblioteko.user;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteko.biblioteko.exception.EmailAlreadyExistsException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserDTO userDTO) throws IllegalArgumentException, EmailAlreadyExistsException {
    	
    	String name = userDTO.getName();
    	String email = userDTO.getEmail();
    	String password = userDTO.getPassword();
    	String role = userDTO.getRole();
    	
        if(name.isBlank() || name.isEmpty()) throw new IllegalArgumentException("Nome nao pode ser vazio!");
        if(email.isBlank() || email.isEmpty()) throw new IllegalArgumentException("Email nao pode ser vazio!");
        if(password.isBlank() || password.isEmpty()) throw new IllegalArgumentException("Senha nao pode ser vazia!");
        if(role.isBlank() || role.isEmpty()) throw new IllegalArgumentException("Voce deve escolher o seu tipo de usuario: professor ou aluno.");
        if(!role.equals("ALUNO") && !role.equals("PROFESSOR")) throw new IllegalArgumentException("Voce deve ser ou professor ou aluno.");
        if(userRepository.existsByEmail(email)) throw new EmailAlreadyExistsException("Este email nao esta disponivel.");
        
        User user = new User(name, email, password, role);
        
        return userRepository.save(user);
        
    }
    
    public UserDTO getUserDetails(UUID userId) throws UserNotFoundException {
    	
    	if(!userRepository.existsById(userId)) throw new UserNotFoundException("Usuario nao encontrado.");
    	
    	User user = userRepository.findById(userId).get();
    	
    	return new UserDTO(user.getName(), user.getEmail(), "", user.getRole());
    	
    }
    
    public void editUserDetails(UUID userId, UserDTO userDTO) throws UserNotFoundException, IllegalArgumentException, EmailAlreadyExistsException {
    	
    	if(!userRepository.existsById(userId)) throw new UserNotFoundException("Usuario nao encontrado.");
    	
    	String name = userDTO.getName();
    	String email = userDTO.getEmail();
    	String password = userDTO.getPassword();
    	String role = userDTO.getRole();
    	
    	if(name.isBlank() || name.isEmpty()) throw new IllegalArgumentException("Nome nao pode ser vazio!");
        if(email.isBlank() || email.isEmpty()) throw new IllegalArgumentException("Email nao pode ser vazio!");
        if(password.isBlank() || password.isEmpty()) throw new IllegalArgumentException("Senha nao pode ser vazia!");
        
        User user = userRepository.findById(userId).get();
        
        if(!user.getRole().equals(role)) throw new IllegalArgumentException("Voce nao pode alterar o tipo de usuario.");
        if(userRepository.existsByEmail(email)) throw new EmailAlreadyExistsException("Este email nao esta disponivel.");
        
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        
        userRepository.save(user);
    	
    }
    
    public void deleteUser(UUID userId) throws UserNotFoundException {
    	
    	if(!userRepository.existsById(userId)) throw new UserNotFoundException("Usuario nao encontrado.");
    	
    	User user = userRepository.findById(userId).get();
    	
    	userRepository.delete(user);
    	
    }
}

