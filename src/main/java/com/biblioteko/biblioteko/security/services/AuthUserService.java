package com.biblioteko.biblioteko.security.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.biblioteko.biblioteko.exception.UnauthenticatedUserException;
import com.biblioteko.biblioteko.user.User;
import com.biblioteko.biblioteko.user.UserDTO;
import com.biblioteko.biblioteko.user.UserRepository;
import com.biblioteko.biblioteko.utils.UserMapper;

@Service
public class AuthUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public boolean checkId(UUID id) {
		
		try {
			return getCurrentUser().getId().equals(id);
		}catch(UnauthenticatedUserException e) {
			return false;
		}
		
	}
	
	public UserDTO getCurrentUser() throws UnauthenticatedUserException {
		
		if(!isAuthenticated()) throw new UnauthenticatedUserException("Usuário não autenticado.");
		
		UserDetails currDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findByEmail(currDetails.getUsername()).get();
		return UserMapper.convertToUserDTO(user);
		
	}
	
	public boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return !(authentication instanceof AnonymousAuthenticationToken);
	}
	
	public boolean isProf() {
		try {
			return getCurrentUser().getRole().equals("PROFESSOR");
		}catch(UnauthenticatedUserException e) {
			return false;
		}
	}

}
