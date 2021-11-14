package br.com.pedroxsqueiroz.stranding.services.impl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;

import br.com.pedroxsqueiroz.stranding.dtos.TokenDto;
import br.com.pedroxsqueiroz.stranding.exception.TokenException;
import br.com.pedroxsqueiroz.stranding.services.AuthorizationService;
import br.com.pedroxsqueiroz.stranding.services.UserPasswordService;
import br.com.pedroxsqueiroz.stranding.services.UserService;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
	
	@Autowired
	private UserPasswordService passwordService;
	
	@Autowired
	private UserService userService;
	
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

	private TokenDto createToken(String login) {
		
		Algorithm algorithm = Algorithm.HMAC256( this.getEncriptionKey() );
		
		String token = JWT	.create()
							.withPayload(Map.of("login", login))
							.sign(algorithm);
		
		return new TokenDto(token);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return this.userService.getByLogin(username);
	}

	@Override
	public String encode(CharSequence rawPassword) {

		String passwordHash = Hashing	.sha256()
				.hashString(rawPassword, StandardCharsets.UTF_8)
				.toString();
		
		return passwordHash;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return this.encode(rawPassword).equals(encodedPassword);
	}

	@Override
	public TokenDto createToken(Authentication auth) {
		return this.createToken(auth.getName());
	}

	
}
