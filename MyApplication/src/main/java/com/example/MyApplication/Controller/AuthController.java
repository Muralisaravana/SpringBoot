package com.example.MyApplication.Controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.MyApplication.Model.User;
import com.example.MyApplication.Repository.UserRepository;
import com.example.MyApplication.Service.UserService;


@Controller
public class AuthController {
	
	private final UserService userService;
	private final UserRepository userRepository;
	
	public AuthController(UserService userService, UserRepository userRepository) {
		this.userService = userService;
		this.userRepository = userRepository;
	}
	
	@GetMapping("/")
	public String home(Model model,Principal principal) {
		model.addAttribute("user", principal != null ? principal.getName() : null);
	    return "home";
	}
	
	@GetMapping("/signup")
	public String showSignupForm(Model model) {
	    model.addAttribute("user", new User());
	    return "signup";
	}
	@PostMapping("/signup")
	public String processSignup(@ModelAttribute("user") User user,Model model) {
		try {
			userService.registerUser(user);
			return "redirect:/signin?registered";
		}
		catch(IllegalArgumentException ex) {
			model.addAttribute("error",ex.getMessage());
			model.addAttribute("user", user);
			return "signup";
		}
		catch(Exception ex) {
			model.addAttribute("error","Something went wrong.Please try again later");
			model.addAttribute("user", user);
			return "signup";

		}
	}
	
	@GetMapping("/signin")
	public String showSignin(@RequestParam(value = "error", required = false) String error,
							 @RequestParam(value = "logout", required = false) String logout,
                             @RequestParam(value = "registered", required = false) String registered,
                             Model model) {
		
		if (error != null) model.addAttribute("error", "Invalid credentials");
        if (logout != null) model.addAttribute("message", "You have been logged out");
        if (registered != null) model.addAttribute("message", "Registration successful. Please login.");
        return "signin";
		
	}
	
	

}
