package br.com.pedroxsqueiroz.stranding.services.impl;

import java.util.Base64;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pedroxsqueiroz.stranding.exception.TokenException;
import br.com.pedroxsqueiroz.stranding.services.AuthorizationService;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
	
	@Value("${encryptionKey}")
	public void setEncriptionKey(String key) 
	{
		this.encriptionKey = key;
	}
	
	private String getEncriptionKey() 
	{
		return this.encriptionKey;
	}
	
	private String encriptionKey;
	
	@Override
	public DecodedJWT decodeToken(String token) throws TokenException {
		
		if( token == null || Strings.isEmpty(token)) 
		{	
			throw new TokenException("Token is empty");
		}
		
		if(!token.startsWith("Bearer")) 
		{
			throw new TokenException("Token is malformed, should starts with 'Bearer'.");
		}
		
		String[] tokenParts = token.split(" ");
		
		if(tokenParts.length != 2) 
		{
			throw new TokenException(
						"Token is malformed, should contains two parts, "
					+ 	"first is 'Bearer' and  thesecond is the token value.");
		}
		
		String tokenValue = tokenParts[1];
		
		Algorithm algorithm = Algorithm.HMAC256( this.getEncriptionKey() );
		
		
		String tokenRawPayload = null;
		
		try {
			
			DecodedJWT decodedToken = 	JWT	.require(algorithm)
					.build()
					.verify(tokenValue);

			tokenRawPayload = new String(Base64.getDecoder().decode(decodedToken.getPayload()));

			ObjectMapper deserializer = new ObjectMapper();

			JsonNode tokenPayload = deserializer.readTree(tokenRawPayload);
			
			if(!tokenPayload.has("login")) 
			{
				throw new TokenException("Field 'login' is required on token body");
			}
			
			return decodedToken;
		}
		catch(SignatureVerificationException signatureException) {
			
			throw new TokenException("Token value is malformed, impossible to decrypt");
			
		}
		catch (JsonProcessingException e) {

			throw new TokenException(String.format("Content Json %s of token is malformed", tokenRawPayload));

		}
		
	}

	@Override
	public boolean login(String login, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
