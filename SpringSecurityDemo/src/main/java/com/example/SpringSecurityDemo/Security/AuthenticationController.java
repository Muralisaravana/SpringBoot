package com.example.SpringSecurityDemo.Security;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	
	public AuthenticationController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){
		
		UsernamePasswordAuthenticationToken  authInput = new UsernamePasswordAuthenticationToken (request.getUsername(),request.getPassword());
		Authentication authentication = authenticationManager.authenticate(authInput);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String token = jwtUtil.generateToken(userDetails.getUsername());
		AuthResponse authResponse = new AuthResponse(token);
		return ResponseEntity.ok(authResponse);
		
	}
	
	
	public static class AuthRequest{
		private String username;
		private String password;
		
        public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
	}
	
	public static class AuthResponse{
		private  String token;

        public AuthResponse(String token) {
			this.token = token;
		}


		public String getToken() {
			return token;
		}

	}

}
