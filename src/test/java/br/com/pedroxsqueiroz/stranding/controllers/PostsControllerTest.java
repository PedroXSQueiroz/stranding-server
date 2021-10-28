package br.com.pedroxsqueiroz.stranding.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
@Sql( 	scripts = { "/migrations/V001__starting_schema.sql"
					,"/feedInitialData/initial_posts_and_users.sql" }
		,executionPhase = ExecutionPhase.BEFORE_TEST_METHOD )
@Sql( 	scripts = { "/regrations/V001__droping_schema.sql" }
		,executionPhase = ExecutionPhase.AFTER_TEST_METHOD )
class PostsControllerTest {
	
	private MockMvc mvc;
	
	@BeforeAll
	void setup() 
	{
		this.mvc = MockMvcBuilders	.standaloneSetup(new PostsController())
									.build();
	}
	
	
	
}
