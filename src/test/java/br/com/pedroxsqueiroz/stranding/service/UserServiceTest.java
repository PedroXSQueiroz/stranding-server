package br.com.pedroxsqueiroz.stranding.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.pedroxsqueiroz.stranding.exception.TokenException;
import br.com.pedroxsqueiroz.stranding.models.User;
import br.com.pedroxsqueiroz.stranding.services.AuthorizationService;
import br.com.pedroxsqueiroz.stranding.services.UserService;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@Sql( 	scripts = { "/migrations/V001__starting_schema.sql"
		,"/feedInitialData/initial_posts_and_users.sql" }
		,executionPhase = ExecutionPhase.BEFORE_TEST_METHOD )
@Sql( 	scripts = { "/regrations/V001__droping_schema.sql" }
		,executionPhase = ExecutionPhase.AFTER_TEST_METHOD )
class UserServiceTest {

	@Autowired
	private AuthorizationService authService;
	
	@Test
	void shouldGetUserFromAuthentication() throws TokenException, JsonMappingException, JsonProcessingException 
	{
		DecodedJWT token = this.authService.decodeToken("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dpbiI6ImR1bW15In0.6Ol7SoGvNoNB9e1yqRV-bK3l8U7yUFWEktdGhcKWp_Y");
		String loginFromToken = AuthorizationService.getLoginFromToken(token);
		
		assertEquals( "dummy", loginFromToken );
		
	}
	
	@Test
	void shouldThrowExceptionWithEnmptyToken() 
	{
		
		TokenException emptyException = assertThrows(TokenException.class, () -> {
			this.authService.decodeToken("");
		});
		
		assertEquals("Token is empty", emptyException.getMessage());
		
		TokenException nullException = assertThrows(TokenException.class, () -> {
			this.authService.decodeToken(null);
		});
		
		assertEquals("Token is empty", nullException.getMessage());
		
	}
	
	@Test
	void shouldThrowExceptionWithMalformedToken () 
	{
		
		TokenException noBearerException = assertThrows(TokenException.class, () -> 
			this.authService.decodeToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dpbiI6ImR1bW15In0.6Ol7SoGvNoNB9e1yqRV-bK3l8U7yUFWEktdGhcKWp_Y")
		);
		
		assertEquals("Token is malformed, should starts with 'Bearer'.", noBearerException.getMessage());
		
		TokenException noTokenValueException = assertThrows(TokenException.class, () -> 
			this.authService.decodeToken("Bearer ")
		);
		
		
		assertEquals("Token is malformed, should contains two parts, "
					+"first is 'Bearer' and  thesecond is the token value.", noTokenValueException.getMessage());
		
		TokenException wrongTokenException = assertThrows( TokenException.class, () ->  
			this.authService.decodeToken("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dpbiI6ImR1bW15In0.6Ol7SoGvNoNB9e1yqRV-bK3l8U7yUFWEktdGhcK")
		);
		
		assertEquals( "Token value is malformed, impossible to decrypt", wrongTokenException.getMessage() );
		
		TokenException wrongPayloadException = assertThrows( TokenException.class, () ->  
			this.authService.decodeToken("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJub2xvZ2luIjoiZHVtbXkifQ.DCP91mk1W_mGN4SiG4Ho3djV5cJ3GF2FVGA3oGaRq4M")
		);
		
		assertEquals( "Field 'login' is required on token body", wrongPayloadException.getMessage() );
		
	}
	
}
