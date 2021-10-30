package br.com.pedroxsqueiroz.stranding.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.pedroxsqueiroz.stranding.dtos.PostDto;
import br.com.pedroxsqueiroz.stranding.models.Post;
import br.com.pedroxsqueiroz.stranding.models.User;
import br.com.pedroxsqueiroz.stranding.services.PostService;
import br.com.pedroxsqueiroz.stranding.services.impl.PostServiceImpl;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@Sql( 	scripts = { "/migrations/V001__starting_schema.sql"
		,"/feedInitialData/initial_posts_and_users.sql" }
		,executionPhase = ExecutionPhase.BEFORE_TEST_METHOD )
@Sql( 	scripts = { "/regrations/V001__droping_schema.sql" }
		,executionPhase = ExecutionPhase.AFTER_TEST_METHOD )
class PostServiceTest {
	
	@Autowired
	public PostService postService = new PostServiceImpl();
	
	@Test
	void shouldCreateNewPostToUser() 
	{
		User dummyUser = User.builder()
			.id(UUID.fromString("c2c15f60-2f0d-11ec-8d3d-0242ac130003"))
			.build();
		
		String dummyPostContent = "O cuidado em identificar pontos críticos no novo modelo estrutural aqui preconizado oferece uma interessante oportunidade para verificação dos modos de operação convencionais.";
		
		Post post = this.postService.add(
							dummyUser
							,PostDto.builder()
								.content(dummyPostContent)
								.build()
						);
		
		assertNotNull(post.getId());
		assertEquals(dummyPostContent, post.getContent());
		assertEquals(dummyUser, post.getCreator());
		assertTrue( Instant.now().getEpochSecond() - post.getCreationDate().getEpochSecond() < 1 );
	}
	
}
