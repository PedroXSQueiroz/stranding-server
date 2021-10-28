package br.com.pedroxsqueiroz.stranding.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.pedroxsqueiroz.stranding.exception.TokenException;
import br.com.pedroxsqueiroz.stranding.models.User;
import br.com.pedroxsqueiroz.stranding.services.UserService;

@Component
public class AuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private UserService userService;
	
	@Override
	protected void doFilterInternal(
				HttpServletRequest request, 
				HttpServletResponse response, 
				FilterChain filterChain)
			
		throws ServletException, IOException {
		
		String header = request.getHeader("Authorization");
		
		try {
			
			User user = this.userService.getByToken(header);
			
			SecurityContextHolder.getContext().setAuthentication(
													new UsernamePasswordAuthenticationToken(user, null) 
												);
			
			filterChain.doFilter(request, response);
			
		} catch (TokenException e) {
			
			ObjectMapper serializer = new ObjectMapper();
			ObjectNode errorMessageRoot = serializer.createObjectNode();
			errorMessageRoot.put("error", "UNAUTHORIZED");
			errorMessageRoot.put("message", e.getMessage());
			serializer.writeValue(response.getOutputStream(), errorMessageRoot);
			
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.setContentType("application/json");
				
		}
		
	}

}
