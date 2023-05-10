package com.biblioteko.biblioteko.user;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
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
   public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
      User user = userService.createUser(userDTO);
      return ResponseEntity.created(URI.create("/users" + user.getId())).body(userDTO);
   }
}
