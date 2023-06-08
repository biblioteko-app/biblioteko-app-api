package com.biblioteko.biblioteko.user;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biblioteko.biblioteko.book.Book;
import com.biblioteko.biblioteko.book.BookDTO;
import com.biblioteko.biblioteko.book.BookService;
import com.biblioteko.biblioteko.exception.EmailAlreadyExistsException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;
import com.biblioteko.biblioteko.read.Read;

import com.biblioteko.biblioteko.roles.Role;
import com.biblioteko.biblioteko.roles.RoleEnum;
import com.biblioteko.biblioteko.roles.RoleRepository;
import com.biblioteko.biblioteko.utils.BookMapper;
import com.biblioteko.biblioteko.utils.UserMapper;
import com.biblioteko.biblioteko.exception.EmailAlreadyExistsException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    PasswordEncoder encoder;

    public UserDTO createUser(NewUserDTO newUserDTO) throws IllegalArgumentException, EmailAlreadyExistsException {
    	
    	String name = newUserDTO.getName();
    	String email = newUserDTO.getEmail();
    	String password = newUserDTO.getPassword();
    	String role = newUserDTO.getRole();
    	
        if(name.isBlank() || name.isEmpty()) throw new IllegalArgumentException("Nome nao pode ser vazio!");
        if(email.isBlank() || email.isEmpty()) throw new IllegalArgumentException("Email nao pode ser vazio!");
        if(password.isBlank() || password.isEmpty()) throw new IllegalArgumentException("Senha nao pode ser vazia!");
        if(role.isBlank() || role.isEmpty()) throw new IllegalArgumentException("Voce deve escolher o seu tipo de usuario: professor ou aluno.");
        if(!role.equals("ALUNO") && !role.equals("PROFESSOR")) throw new IllegalArgumentException("Voce deve ser ou professor ou aluno.");
        if(userRepository.existsByEmail(email)) throw new EmailAlreadyExistsException("Este email nao esta disponivel.");
        
        User user = new User(name, email, encoder.encode(password), role);
        
        Set<Role> securityRoles = new HashSet<Role>();
        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Erro: Security role nao encontrado."));
        securityRoles.add(userRole);
        
        user.setSecurityRoles(securityRoles);
        
        return UserMapper.convertToUserDTO(userRepository.save(user));
    }
    
    public UserDTO getUserDetails(UUID userId) throws UserNotFoundException {
        User user = findUserById(userId);
    	return UserMapper.convertToUserDTO(user);
    }
    
    public void editUserDetails(UUID userId, UserDTO newUserDTO) throws UserNotFoundException, IllegalArgumentException, EmailAlreadyExistsException {
    	
    	User user = findUserById(userId);
    	
    	String name = newUserDTO.getName();
    	String email = newUserDTO.getEmail();
    	String role = newUserDTO.getRole();
    	
    	if(name.isBlank() || name.isEmpty()) throw new IllegalArgumentException("Nome nao pode ser vazio!");
        if(email.isBlank() || email.isEmpty()) throw new IllegalArgumentException("Email nao pode ser vazio!");
        
        if(!user.getRole().equals(role)) throw new IllegalArgumentException("Voce nao pode alterar o tipo de usuario.");
        
        if((!user.getEmail().equals(email)) && userRepository.existsByEmail(email)) throw new EmailAlreadyExistsException("Este endereco de email nao esta disponivel.");

        user.setName(name);
        user.setEmail(email);
        
        userRepository.save(user);	
    }
    
    public void changePassword(UUID userId, String newPass) throws UserNotFoundException, IllegalArgumentException {
    	
    	User user = findUserById(userId);
    	
    	if(newPass.isBlank() || newPass.isEmpty()) throw new IllegalArgumentException("Senha nao pode ser vazia!");
    	
    	user.setPassword(encoder.encode(newPass));
    	
    	userRepository.save(user);
    	
    }
    
    public void deleteUser(UUID userId) throws UserNotFoundException {
    	User user = findUserById(userId);
    
    	userRepository.delete(user);
    }

    public User findUserById(UUID userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()) throw new UserNotFoundException("Usuario nao encontrado.");
        return user.get();
    }

    public void addRead(Read read, User user) {
        user.addRead(read);
        userRepository.save(user);
    }

    public void addBookToStarredList(User user, Book book) {
        user.addFavoriteBook(book);
        userRepository.save(user);
    }

    public Set<BookDTO> getFavoriteBooks(UUID userId) throws UserNotFoundException{
        User user = findUserById(userId);
        return user.getStarredBooks().stream().map(b -> BookMapper.convertToBookDTO(b)).collect(Collectors.toSet());

    }

}

