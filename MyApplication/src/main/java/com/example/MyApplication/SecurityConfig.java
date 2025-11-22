package com.example.MyApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.MyApplication.Security.CustomUserDetailsService;

@Configuration
public class SecurityConfig {
	
	private final CustomUserDetailsService customUserDetailsService;

	public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
		this.customUserDetailsService = customUserDetailsService;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }	
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())   // disable CSRF for simplicity during development
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/signup", "/signin", "/css/**").permitAll() // public pages
                .anyRequest().authenticated()   // everything else needs login
            )
            .formLogin(form -> form
                .loginPage("/signin")               // custom login page
                .loginProcessingUrl("/perform_login") // form POST action
                .usernameParameter("email")         // field name for username
                .passwordParameter("password")      // field name for password
                .defaultSuccessUrl("/", true)       // redirect after successful login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/signin?logout")
                .permitAll()
            );

        http.authenticationProvider(authenticationProvider());

        return http.build();

}
}