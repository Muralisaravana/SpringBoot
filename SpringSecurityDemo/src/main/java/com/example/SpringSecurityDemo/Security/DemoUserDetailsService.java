package com.example.SpringSecurityDemo.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class DemoUserDetailsService {
	
	@Bean
	public InMemoryUserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder) {
		UserDetails user = User.withUsername("Murali")
				               .password(passwordEncoder.encode("Jumbo123"))
				               .roles("USER")
				               .build();
		UserDetails admin = User.withUsername("Jumbo")
	               .password(passwordEncoder.encode("Jumbo123"))
	               .roles("ADMIN")
	               .build();
		return new InMemoryUserDetailsManager(user,admin);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
