package com.example.EmployeeApplication.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	@Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        var user = User.withUsername("user")
                       .password(passwordEncoder.encode("userpass"))
                       .roles("USER")
                       .build();

        var admin = User.withUsername("admin")
                        .password(passwordEncoder.encode("adminpass"))
                        .roles("ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
	
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	 
	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	          .csrf().disable() // For API demo: explain to interviewer why you disabled CSRF
	          .authorizeHttpRequests()
	            .requestMatchers("/h2-console/**", "/actuator/health", "/auth/**").permitAll()
	            .anyRequest().authenticated()
	          .and()
	          .httpBasic();

	        // Allow h2-console frames
	        http.headers().frameOptions().sameOrigin();

	        return http.build();
	    }
}
