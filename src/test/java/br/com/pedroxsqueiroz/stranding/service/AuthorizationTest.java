package br.com.pedroxsqueiroz.stranding.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.pedroxsqueiroz.stranding.dtos.TokenDto;
import br.com.pedroxsqueiroz.stranding.exception.TokenException;
import br.com.pedroxsqueiroz.stranding.filters.AuthorizationFilter;
import br.com.pedroxsqueiroz.stranding.models.User;
import br.com.pedroxsqueiroz.stranding.services.AuthorizationService;
import br.com.pedroxsqueiroz.stranding.services.UserService;
import br.com.pedroxsqueiroz.stranding.services.impl.AuthorizationServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
@Sql( 	scripts = { "/migrations/V001__starting_schema.sql"
					,"/feedInitialData/initial_posts_and_users.sql" }
		,executionPhase = ExecutionPhase.BEFORE_TEST_METHOD )
@Sql( 	scripts = { "/regrations/V001__droping_schema.sql" }
		,executionPhase = ExecutionPhase.AFTER_TEST_METHOD )
class AuthorizationTest {
	
	public AuthorizationTest( @Autowired AuthorizationServiceImpl authService)
	{
		this.authService = authService;
	}
	
	@Mock
	public HttpServletRequest request;
	
	@Mock
	public HttpServletResponse response;
	
	@Mock
	public FilterChain chain;
	
	@Mock
	public AuthorizationServiceImpl mockAuthService;
	
	@InjectMocks
	public AuthorizationServiceImpl authService;
	
	@Mock
	public UserService userService;
	
	@InjectMocks
	public AuthorizationFilter filter = new AuthorizationFilter();
	
	private User dummyUser = User	.builder()
									.id(UUID.fromString("c2c15f60-2f0d-11ec-8d3d-0242ac130003"))
									.build();
	
	@Value("${encryptionKey}")
	private String encryptionKey;
							
	@BeforeAll
	void setup() throws TokenException 
	{
		Mockito	.doReturn("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dpbiI6ImR1bW15In0.6Ol7SoGvNoNB9e1yqRV-bK3l8U7yUFWEktdGhcKWp_Y")
				.when(this.request)
				.getHeader("Authorization");
		
		Mockito	.doCallRealMethod()
				.when(this.mockAuthService)
				.decodeToken(Mockito.anyString());
		
		Mockito	.doCallRealMethod()
				.when(this.mockAuthService)
				.setEncriptionKey(Mockito.anyString());
		
		Mockito	.doCallRealMethod()
				.when(this.mockAuthService)
				.login(Mockito.any(), Mockito.anyString());
		
		this.mockAuthService.setEncriptionKey(this.encryptionKey);
		this.authService.setEncriptionKey(this.encryptionKey);
		
		Mockito	.doReturn(dummyUser)
				.when(this.userService)
				.getByLogin("dummy");	
	}
	
	@Test
	void shouldPermit() throws ServletException, IOException
	{
		filter.doFilter(request, response, chain);
		
		Mockito.verify(this.chain, Mockito.times(1)).doFilter( request, response );
		
		Object loggedUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		assertEquals(this.dummyUser, loggedUser);
	}
	
	@Test
	void shouldLogin() throws TokenException, JsonProcessingException 
	{
		TokenDto token = this.authService.login("dummy", "P0k0$R#Med87RIaY");
		
		DecodedJWT decodedToken = this.authService.decodeToken("Bearer " + token.getToken());
		
		String login = AuthorizationService.getLoginFromToken(decodedToken);
		
		assertEquals("dummy", login);
	}
	
	
}
