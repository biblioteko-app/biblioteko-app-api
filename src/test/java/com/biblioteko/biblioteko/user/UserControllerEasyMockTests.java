package com.biblioteko.biblioteko.user;

import org.easymock.*;
import java.util.UUID;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jmock.Expectations;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.biblioteko.biblioteko.exception.EmailAlreadyExistsException;
import com.biblioteko.biblioteko.exception.UserAlreadyLoggedInException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;
import com.biblioteko.biblioteko.security.jwt.JwtUtils;
import com.biblioteko.biblioteko.security.roles.RoleRepository;
import com.biblioteko.biblioteko.security.services.AuthUserService;
import com.biblioteko.biblioteko.studentClass.StudentClassService;

import java.util.Optional;

public class UserControllerEasyMockTests extends EasyMockSupport {
    
    @Rule
    public EasyMockRule rule = new EasyMockRule(this);
    
    @Mock
    private UserService userService;
    
    @Mock
    private StudentClassService studentClassService;
    
    @Mock
    private AuthUserService authUserService;
    
    @Mock
    private AuthenticationManager authenticationManager;
    
    @Mock
    private JwtUtils jwtUtils;
    
    @Mock
    private RoleRepository roleRepository;
    
    @TestSubject
    private UserController userController = new UserController(userService, studentClassService, authUserService, authenticationManager, jwtUtils, roleRepository);


    @Test
    public void userController_shouldReturnAnResponseEntityWithInternalServerError_whenUserServiceThrowsAnException() throws UserNotFoundException, EmailAlreadyExistsException, IllegalArgumentException, UserAlreadyLoggedInException {

        // NewUserDTO userDTO = anyObject(NewUserDTO.class);

        
        // Arrange

        userService.createUser(
                anyObject(NewUserDTO.class)
        );

        expectLastCall().andThrow(new Exception());
            
        ResponseEntity response = this.userController.createUser(new NewUserDTO());
        
        replayAll();

        // Act

        verifyAll();

        // Assert
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
