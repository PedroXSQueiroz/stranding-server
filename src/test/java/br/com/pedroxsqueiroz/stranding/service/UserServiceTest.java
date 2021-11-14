package br.com.pedroxsqueiroz.stranding.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.hash.Hashing;

import br.com.pedroxsqueiroz.stranding.dtos.UserDto;
import br.com.pedroxsqueiroz.stranding.exception.TokenException;
import br.com.pedroxsqueiroz.stranding.models.User;
import br.com.pedroxsqueiroz.stranding.services.AuthorizationService;
import br.com.pedroxsqueiroz.stranding.services.UserPasswordService;
import br.com.pedroxsqueiroz.stranding.services.UserService;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@Sql( 	scripts = { "/migrations/V001__starting_schema.sql"
		,"/feedInitialData/initial_posts_and_users.sql" }
		,executionPhase = ExecutionPhase.BEFORE_TEST_METHOD )
@Sql( 	scripts = { "/regrations/V001__droping_schema.sql" }
		,executionPhase = ExecutionPhase.AFTER_TEST_METHOD )
class UserServiceTest {

	UserServiceTest	(@Autowired AuthorizationService authService
					,@Autowired UserService userService)
	{
		this.authService = authService;
		this.userService = userService;
	}
	
	@BeforeAll
	public void setup() 
	{
		
	}
	
	@Mock
	public UserPasswordService passwordService;
	
	private final AuthorizationService authService;
	
	@InjectMocks
	private final UserService userService;
	
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
	
	@Test
	void shouldSave() 
	{
		
		UserDto userDto = UserDto.builder()
								.name("Runtime Saved")
								.login("runtime_saved")
								.password("runtime_saved_password")
								.build();
		
		User created = this.userService.create(userDto);
		
		assertNotNull( created.getId() );
		
		assertEquals(userDto.getLogin(), created.getLogin());
		assertEquals(userDto.getName(), created.getName());
		
		Mockito.verify(this.passwordService, Mockito.times(1)).save(Mockito.any());
		
	}
	
	@Test
	@Transactional
	void shouldAddFriendTest()
	{
		List<User> newFriends = this.userService.addFriends(
													UUID.fromString("c2c15f60-2f0d-11ec-8d3d-0242ac130003")
													,List.of( 	
														UUID.fromString("e76e1f52-2f11-11ec-8d3d-0242ac130003")
														,UUID.fromString("efece06e-2f11-11ec-8d3d-0242ac130003")
														,UUID.fromString("f7a77148-2f11-11ec-8d3d-0242ac130003")
														,UUID.fromString("ff395f7a-2f11-11ec-8d3d-0242ac130003")
														)  
													);
		
		newFriends.forEach( friend -> {
			
			List<User> inverseFriends = friend.getFriends();
			
			assertEquals(1, inverseFriends.size());
			
			User user = inverseFriends.get(0);
			
			assertEquals("c2c15f60-2f0d-11ec-8d3d-0242ac130003", user.getId().toString());
			
		});
		
	}
	
}
