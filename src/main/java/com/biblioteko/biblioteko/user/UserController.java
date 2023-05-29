package com.biblioteko.biblioteko.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users/")
public class UserController {

    @Autowired
    private UserService userService;

   @PostMapping
   public ResponseEntity<?> createUser(@RequestBody NewUserDTO newUserDTO) {
      UserDTO userDTO = userService.createUser(newUserDTO);
      return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
   }
}
