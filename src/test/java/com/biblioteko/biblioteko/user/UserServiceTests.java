package com.biblioteko.biblioteko.user;

import org.jmock.Mockery;
import org.jmock.imposters.ByteBuddyClassImposteriser;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import java.util.Set;
import com.biblioteko.biblioteko.book.Book;

import org.hibernate.mapping.List;
import org.jmock.Expectations;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.biblioteko.biblioteko.exception.EmailAlreadyExistsException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;
import com.biblioteko.biblioteko.security.roles.RoleRepository;
import com.biblioteko.biblioteko.security.services.AuthUserService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class UserServiceTests {
    
    // Mockery context = new Mockery();
    // @RegisterExtension
    // private JUnit5Mockery context = new JUnit5Mockery() {{
    //     setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
    // }};

    @RegisterExtension
    private JUnit5Mockery context = new JUnit5Mockery();

    private UserService userService;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthUserService authUserService;

    @BeforeAll
    void setUp() {
        this.userRepository = this.context.mock(UserRepository.class);
        this.roleRepository = this.context.mock(RoleRepository.class);
        // this.authUserService = this.context.mock(AuthUserService.class);
        this.userService = new UserService(userRepository, roleRepository, passwordEncoder, authUserService);
    }


    @Test
    void userService_shouldSaveInRepositoryAnEdittedUser_whenEditUserDetailsIsCalled() throws IllegalArgumentException, UserNotFoundException, EmailAlreadyExistsException {

        // Arrange
        User user = new User(
            "Vitor", 
            "vitor@gmail.com", 
            "123", 
            "professor"
        );
        UserDTO userDTO = new UserDTO(user.getId(), user.getName() + "Oi", user.getEmail(), user.getRole(), new HashSet<>(), new HashSet<>());

        // Expectations
        context.checking(new Expectations() {{
            oneOf(userRepository).findById(userDTO.getId()); will(returnValue(Optional.of(user)));
        }});

        user.setName(userDTO.getName());

        context.checking(new Expectations() {{
            oneOf(userRepository).save(user);
        }});

        // Act
        this.userService.editUserDetails(userDTO.getId(), userDTO);
    }

}
