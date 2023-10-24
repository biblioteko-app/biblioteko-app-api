package com.biblioteko.biblioteko.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.jmock.Expectations;
import org.jmock.junit5.JUnit5Mockery;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import com.biblioteko.biblioteko.exception.EmailAlreadyExistsException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;
import com.biblioteko.biblioteko.security.jwt.JwtUtils;
import com.biblioteko.biblioteko.security.roles.RoleRepository;
import com.biblioteko.biblioteko.security.services.AuthUserService;
import com.biblioteko.biblioteko.studentClass.StudentClassService;

import io.micrometer.core.ipc.http.HttpSender.Response;

import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.RegisterExtension;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class UserControllerTests {
    
    @RegisterExtension
    private JUnit5Mockery context = new JUnit5Mockery() {{
        setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
    }};

    private UserController userController;

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


    @BeforeAll
    void setUp() {
        this.userService = this.context.mock(UserService.class);
        this.userController = new UserController(userService, studentClassService, authUserService, authenticationManager, jwtUtils, roleRepository);
    }

    @Test
    void userController_shouldReturnAnResponseEntityWithInternalServerError_whenUserServiceThrowsAnException() throws UserNotFoundException, EmailAlreadyExistsException {

        // Arrange
        this.context.checking(new Expectations() {{
            oneOf(userService).editUserDetails(with(any(UUID.class)), with(any(UserDTO.class))); 
            will(throwException(new Exception("Lancou")));
        }});

        // Act
        ResponseEntity response = this.userController.editUserDetails(UUID.randomUUID(), new UserDTO());

        // Assert
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
