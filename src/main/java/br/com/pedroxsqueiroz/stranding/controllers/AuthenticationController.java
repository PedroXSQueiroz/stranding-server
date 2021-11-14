package br.com.pedroxsqueiroz.stranding.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedroxsqueiroz.stranding.dtos.AuthDto;
import br.com.pedroxsqueiroz.stranding.dtos.TokenDto;
import br.com.pedroxsqueiroz.stranding.services.AuthorizationService;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthorizationService authService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@PostMapping("/login")
	public TokenDto authentiacate( @RequestBody AuthDto auth ) 
	{
		UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(auth.getLogin() , auth.getPassword());
		Authentication authentication = this.authManager.authenticate(credentials);
		return this.authService.createToken(authentication);
	}
	
}
