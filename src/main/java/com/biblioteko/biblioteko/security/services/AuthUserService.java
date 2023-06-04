package com.biblioteko.biblioteko.security.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.biblioteko.biblioteko.user.User;
import com.biblioteko.biblioteko.user.UserRepository;

@Service
public class AuthUserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public boolean checkId(UUID id) {
		String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		User user = userRepository.findByEmail(username).get();
		return user.getId().equals(id);
	}

}
