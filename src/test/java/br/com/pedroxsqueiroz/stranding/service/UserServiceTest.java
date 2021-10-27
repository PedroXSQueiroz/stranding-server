package br.com.pedroxsqueiroz.stranding.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Collection;
import java.util.UUID;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.jdbc.Sql;

import br.com.pedroxsqueiroz.stranding.exception.TokenException;
import br.com.pedroxsqueiroz.stranding.models.User;
import br.com.pedroxsqueiroz.stranding.services.UserService;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@Sql({ 		"/migrations/V001__starting_schema.sql"
			,"/feedInitialData/initial_posts_and_users.sql" })
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Test
	public void shouldGetUserFromAuthentication() throws TokenException 
	{
		User user = this.userService.getByToken("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dpbiI6ImR1bW15In0.6Ol7SoGvNoNB9e1yqRV-bK3l8U7yUFWEktdGhcKWp_Y");
		
		assertEquals( "c2c15f60-2f0d-11ec-8d3d-0242ac130003", user.getId().toString() );
		
	}
	
}
