package br.com.pedroxsqueiroz.stranding.services;

import java.util.Base64;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pedroxsqueiroz.stranding.exception.TokenException;

public interface AuthorizationService {

	static String getLoginFromToken(DecodedJWT jwt) throws TokenException, JsonMappingException, JsonProcessingException 
	{
		String tokenRawPayload = new String(Base64.getDecoder().decode(jwt.getPayload()));

		ObjectMapper deserializer = new ObjectMapper();

		JsonNode tokenPayload = deserializer.readTree(tokenRawPayload);
		
		if(!tokenPayload.has("login")) 
		{
			throw new TokenException("Field 'login' is required on token body");
		}
		
		String login = tokenPayload.get("login").asText();
		
		return login;
	}
	
	DecodedJWT decodeToken(String token) throws TokenException;

}
