package com.biblioteko.biblioteko.user;

import org.easymock.*;
import static org.easymock.EasyMock.*;
import org.jmock.Expectations;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import com.biblioteko.biblioteko.exception.EmailAlreadyExistsException;
import com.biblioteko.biblioteko.exception.UserNotFoundException;
import com.biblioteko.biblioteko.security.roles.RoleRepository;
import com.biblioteko.biblioteko.security.services.AuthUserService;

import java.util.HashSet;

public class UserServiceEasyMockTests extends EasyMockSupport {
    
    @Rule
    public EasyMockRule rule = new EasyMockRule(this);


    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthUserService authUserService;

    private UserService userService;

    @Before
    public void setUp() {
        this.userService = new UserService(userRepository, roleRepository, passwordEncoder, authUserService);
    }


    @Test
    public void userService_shouldSaveInRepositoryAnEdittedUser_whenEditUserDetailsIsCalled() throws IllegalArgumentException, UserNotFoundException, EmailAlreadyExistsException {

        // Arrange
        User user = new User(
            "Vitor", 
            "vitor@gmail.com", 
            "123", 
            "professor"
        );
        UserDTO userDTO = new UserDTO(user.getId(), user.getName() + "Oi", user.getEmail(), user.getRole(), new HashSet<>(), new HashSet<>());

        // Expectations
        EasyMock.expect(userRepository.findById(userDTO.getId())).andReturn(Optional.of(user));
        user.setName(userDTO.getName());

        expect(userRepository.save(user)).andReturn(user);
        replayAll();

        // Act
        this.userService.editUserDetails(userDTO.getId(), userDTO);
        verifyAll();
    }

}
