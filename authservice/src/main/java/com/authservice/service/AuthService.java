package com.authservice.service;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authservice.payload.APIResponse;
import com.authservice.entity.User;
import com.authservice.payload.UserDto;
import com.authservice.repository.UserRepository;

@Service
public class AuthService {

	@Autowired
	private UserRepository   userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public APIResponse<String> register(UserDto dto) {

		// Api response object
		APIResponse<String> response = new APIResponse<>();

		// check whether Username exists

		if (userRepository.existsByUsername(dto.getUsername())) {
			response.setMessage("user with this username exists");
			response.setStatus(500);
			response.setData("User with user name already exists");
			return response;
		}

		// Check Whether Email Exists
		if (userRepository.existsByEmail(dto.getEmail())) {
			response.setMessage("Registration Failed");
			response.setStatus(500);
			response.setData("User with Email already exists");
			return response;
		}

		// Encode the password before saving that to the database
		
		String encryptedPasword = passwordEncoder.encode(dto.getPassword());
		
		User user = new User();
		BeanUtils.copyProperties(dto, user);
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		
		userRepository.save(user);
			
			response.setMessage("Registration Completed");
			response.setStatus(201);
			response.setData("User has been registred");
			return response;
			
		}
	
	// finally save the user and return response as APIResponse
		
	}

