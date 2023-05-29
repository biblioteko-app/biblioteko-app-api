package com.biblioteko.biblioteko.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteko.biblioteko.exception.UserNotFoundException;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDTO createUser(NewUserDTO newUserDTO) {
        User user = new User();
        user.setName(newUserDTO.getName());
        user.setRole(newUserDTO.getRole());
        return convertToUserDTO(userRepository.save(user));
    }

    public UserDTO convertToUserDTO (User user){
        return new UserDTO(user.getName(), user.getRole(), user.getId());

    }

    public User findUserById(UUID userId) throws UserNotFoundException {
        Optional<User> user = this.userRepository.findById(userId);

        return user.get();
    }

}

