package com.biblioteko.biblioteko.security.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.biblioteko.biblioteko.user.User;
import com.biblioteko.biblioteko.user.UserDTO;
import com.biblioteko.biblioteko.user.UserRepository;
import com.biblioteko.biblioteko.user.UserService;

@Service
public class AuthUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	public boolean checkId(UUID id) {
		String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		User user = userRepository.findByEmail(username).get();
		return user.getId().equals(id);
	}
	
	public UserDTO getCurrentUser() {
		
		UserDetails currDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findByEmail(currDetails.getUsername()).get();
		return userService.convertToUserDTO(user);
		
	}
	
	public boolean isProf() {
		return getCurrentUser().getRole().equals("PROFESSOR");
	}

}
