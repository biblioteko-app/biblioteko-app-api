package com.biblioteko.biblioteko.signinout;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteko.biblioteko.exception.UnauthenticatedUserException;
import com.biblioteko.biblioteko.response.MessageResponse;
import com.biblioteko.biblioteko.response.UserInfoResponse;
import com.biblioteko.biblioteko.security.jwt.JwtUtils;
import com.biblioteko.biblioteko.security.services.AuthUserService;
import com.biblioteko.biblioteko.security.services.UserDetailsImpl;
import com.biblioteko.biblioteko.user.UserDTO;
import com.biblioteko.biblioteko.user.UserLoginDTO;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class Controller {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private AuthUserService authUserService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserLoginDTO loginRequest) {
		
		if(authUserService.isAuthenticated()) {
			return new ResponseEntity<>("Você já está logado no sistema.", HttpStatus.BAD_REQUEST);
		}

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
				.body(new UserInfoResponse(userDetails.getId(),
						userDetails.getEmail(),
						roles));
	}

	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser() {
		
		ResponseEntity<?> resp;
		
		if(authUserService.isAuthenticated()) {
			ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
			resp = ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
					.body(new MessageResponse("Voce foi deslogado. Ate mais!"));
			
		}else {
			resp = new ResponseEntity<>("Você não está logado.", HttpStatus.BAD_REQUEST);
		}
		
		return resp;
	}
	
	@GetMapping("/isAuth")
	public ResponseEntity<?> isAuth() {
		
		int val;
		
		if(authUserService.isAuthenticated()) {
			val = 1;
		}else {
			val = 0;
		}
		
		return new ResponseEntity<>(val, HttpStatus.OK);
	}
	
	@GetMapping("/current")
	public ResponseEntity<?> currentUser() {
		try {
			UserDTO user = authUserService.getCurrentUser();
			return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
		}catch(UnauthenticatedUserException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
