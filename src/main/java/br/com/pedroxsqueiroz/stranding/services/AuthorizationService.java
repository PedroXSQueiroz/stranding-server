package br.com.pedroxsqueiroz.stranding.services;

import java.util.Base64;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pedroxsqueiroz.stranding.dtos.TokenDto;
import br.com.pedroxsqueiroz.stranding.exception.TokenException;

public interface AuthorizationService extends UserDetailsService, PasswordEncoder {

	static String getLoginFromToken(DecodedJWT jwt) throws TokenException, JsonProcessingException 
	{
		String tokenRawPayload = new String(Base64.getDecoder().decode(jwt.getPayload()));

		ObjectMapper deserializer = new ObjectMapper();

		JsonNode tokenPayload = deserializer.readTree(tokenRawPayload);
		
		if(!tokenPayload.has("login")) 
		{
			throw new TokenException("Field 'login' is required on token body");
		}
		
		return tokenPayload.get("login").asText();
	}
	
	DecodedJWT decodeToken(String token) throws TokenException;
	
	TokenDto createToken(Authentication authentication);

}
