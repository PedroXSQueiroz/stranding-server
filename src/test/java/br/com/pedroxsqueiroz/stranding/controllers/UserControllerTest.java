package br.com.pedroxsqueiroz.stranding.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import br.com.pedroxsqueiroz.stranding.dtos.TokenDto;
import br.com.pedroxsqueiroz.stranding.models.User;
import br.com.pedroxsqueiroz.stranding.services.AuthorizationService;
import br.com.pedroxsqueiroz.stranding.services.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
@Sql( 	scripts = { "/migrations/V001__starting_schema.sql"
					,"/feedInitialData/initial_posts_and_users.sql" }
		,executionPhase = ExecutionPhase.BEFORE_TEST_METHOD )
@Sql( 	scripts = { "/regrations/V001__droping_schema.sql" }
		,executionPhase = ExecutionPhase.AFTER_TEST_METHOD )
class UserControllerTest {

	private MockMvc mvc;
	
	@Autowired
	private AuthorizationService authService;
	
	@Mock
	private UserService mockUserService;
	
	
	@InjectMocks
	private UserController userController = new UserController();
	
	@BeforeAll
	void setup() 
	{
		this.mvc = MockMvcBuilders
					.standaloneSetup(this.userController)
					.setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver())
					.build();
	}
	
	@Transactional
	@Test
	@WithUserDetails("dummy")
	void shouldAddFriends() throws Exception 
	{
		
		Mockito.doReturn( List.of(	User.builder()
										.id(UUID.fromString("e76e1f52-2f11-11ec-8d3d-0242ac130003"))
										.build()
									,User.builder()
										.id(UUID.fromString("efece06e-2f11-11ec-8d3d-0242ac130003"))
										.build()
									,User.builder()
										.id(UUID.fromString("f7a77148-2f11-11ec-8d3d-0242ac130003"))
										.build()) )
						.when(this.mockUserService)
						.addFriends(Mockito.any(), Mockito.any());
		
		ArrayNode jsonArrayOfFriends = JsonNodeFactory.instance.arrayNode()
																.add("e76e1f52-2f11-11ec-8d3d-0242ac130003")
																.add("efece06e-2f11-11ec-8d3d-0242ac130003")
																.add("f7a77148-2f11-11ec-8d3d-0242ac130003");
		String requestBody = jsonArrayOfFriends.toString();
		
		TokenDto token = this.authService.createToken( new UsernamePasswordAuthenticationToken("dummy", "P0k0$R#Med87RIaY") );
		
		this.mvc.perform( put("/user/friends" )
								.content(requestBody)
								.header("Authorization", "Bearer " + token.getToken() )
								.contentType(MediaType.APPLICATION_JSON)
						).andExpect(status().isOk())
						.andExpect(result -> {
							
							byte[] responseContentByteArray = result.getResponse().getContentAsByteArray();
							
							ObjectMapper serializer = new ObjectMapper();
							JsonNode response = serializer.readTree(responseContentByteArray);
							
							assertEquals( "e76e1f52-2f11-11ec-8d3d-0242ac130003", response.get(0).asText() );
							assertEquals( "efece06e-2f11-11ec-8d3d-0242ac130003", response.get(1).asText() );
							assertEquals( "f7a77148-2f11-11ec-8d3d-0242ac130003", response.get(2).asText() );
							
					});
	}
	
}
