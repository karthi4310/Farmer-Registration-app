package com.farmer.farmermanagement.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtutil;

	public JwtAuthenticationFilter(JwtUtil jwtutil) {
		this.jwtutil = jwtutil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Get the JWT token from the Authorization header
		String authorizationHeader = request.getHeader("Authorization");
		String jwtToken = null;

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwtToken = authorizationHeader.substring(7);
			// Extract the token
		}

		if (jwtToken != null) {
			try {
				// Validate the token using the jwtutil

				if (!jwtutil.validateToken(jwtToken)) {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token is invalid or expired");
				}

				// Extract claims from the token Claims
				Claims claims = jwtutil.extractClaims(jwtToken);

				// Construct UserDetails from the claims
				String username = claims.get("sub", String.class);

				@SuppressWarnings("unchecked")
				List<String> roles = claims.get("roles", List.class);
				System.out.println("roles");
				// Create a UserDetails object without querying the database
				UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, "",
						roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
				System.out.println("userdetails");
				// Set the authentication in the security context
				Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (ExpiredJwtException e) { // Handle token expiration
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token is expired");
				return;
			} catch (Exception e) { // Handle other exceptions
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token is invalid ");
				return;
			}
		}

		// Continue the filter chain
		filterChain.doFilter(request, response);
	}
}
