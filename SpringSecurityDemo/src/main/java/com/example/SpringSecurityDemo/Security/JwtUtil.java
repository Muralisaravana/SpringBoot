package com.example.SpringSecurityDemo.Security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private final Key key;
	private final long jwtExpirationMs;
	
	public JwtUtil(@Value("${jwt.secret}") String secret,@Value("${jwt.expiration-ms}") long jwtExpirationMs)
	{
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
		this.jwtExpirationMs = jwtExpirationMs;
	}
	
	public String generateToken(String userName) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + jwtExpirationMs);
		
		return Jwts.builder()
				   .setSubject(userName)
				   .setIssuedAt(now)
				   .setExpiration(expiry)
				   .signWith(key,SignatureAlgorithm.HS256)
				   .compact();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			    .setSigningKey(key)
			    .build()
			    .parseClaimsJws(token);
			return true;
		}
		catch(JwtException | IllegalArgumentException ex) {
			return false;
		}
		
	}
    public String getUsernameFromToken(String token) {
    	return getSubjectClaim(token);
    }

    private String getSubjectClaim(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

}
