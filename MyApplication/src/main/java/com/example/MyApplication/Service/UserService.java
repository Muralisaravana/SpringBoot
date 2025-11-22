package com.example.MyApplication.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.MyApplication.Model.User;
import com.example.MyApplication.Repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	public User registerUser(User user) {
		
		if(userRepository.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("Email already Registered");
		}
		
		String encryptedPassword = passwordEncoder.encode(user.getPassword());	
		user.setPassword(encryptedPassword);
		
		return userRepository.save(user);
	}
	
	

}
