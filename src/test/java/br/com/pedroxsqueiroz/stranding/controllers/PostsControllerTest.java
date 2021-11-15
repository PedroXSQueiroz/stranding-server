package br.com.pedroxsqueiroz.stranding.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.net.MediaType;

import br.com.pedroxsqueiroz.stranding.exception.MediaServiceNotAvailable;
import br.com.pedroxsqueiroz.stranding.exception.PostNotFoundException;
import br.com.pedroxsqueiroz.stranding.models.PostMedia;
import br.com.pedroxsqueiroz.stranding.services.FeedService;
import br.com.pedroxsqueiroz.stranding.services.PostService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
@Sql( 	scripts = { "/migrations/V001__starting_schema.sql"
					,"/feedInitialData/initial_posts_and_users.sql" }
		,executionPhase = ExecutionPhase.BEFORE_TEST_METHOD )
@Sql( 	scripts = { "/regrations/V001__droping_schema.sql" }
		,executionPhase = ExecutionPhase.AFTER_TEST_METHOD )
class PostsControllerTest {
	
	private MockMvc mvc;
	
	@Mock
	private FeedService feedService;
	
	@Mock
	private PostService postService;
	
	@InjectMocks
	private PostsController postsController = new PostsController();
	
	@BeforeAll
	public void setup() throws MediaServiceNotAvailable, PostNotFoundException 
	{
		this.mvc = MockMvcBuilders.standaloneSetup(this.postsController).build();
		
	}
	
	@Test
	public void shouldAttachMediaToPost() throws Exception 
	{
		final UUID firstDummyMediaId = UUID.randomUUID();
		final UUID secondDummyMediaId = UUID.randomUUID();
		
		Mockito.doReturn(
				List.of(
					PostMedia	.builder()
								.id(firstDummyMediaId)
								.internalId("1")
								.name("amo_paçoca.jpg")
								.build()
					,PostMedia	.builder()
								.id(secondDummyMediaId)
								.internalId("2")
								.name("desmotivacional.jpg")
								.build()
					)
				).when(this.postService)
				.attachMedias(Mockito.any(), Mockito.any());
		
		InputStream dummyMedia = this.getClass().getClassLoader().getResourceAsStream("media/amo_paçoca.jpg");
		InputStream dummyMedia1 = this.getClass().getClassLoader().getResourceAsStream("media/desmotivacional.jpg");
		
		MockMultipartFile mockMultipartFile = new MockMultipartFile("medias", "amo_paçoca.jpg", MediaType.ANY_IMAGE_TYPE.toString(),  dummyMedia);
		MockMultipartFile mockMultipartFile1 = new MockMultipartFile("medias", "desmotivacional.jpg", MediaType.ANY_IMAGE_TYPE.toString() , dummyMedia1);
		
		this.mvc.perform(
				multipart(  "/posts/{postId}/media", "174d6195-000b-465c-bcda-1ee5ee2da36f")
				.file(mockMultipartFile)
				.file(mockMultipartFile1))
				.andExpect(status().isOk())
				.andExpect( result -> {
					
					byte[] responseContentBytes = result.getResponse().getContentAsByteArray();
					JsonNode responseContent = new ObjectMapper().readTree(responseContentBytes);
					
					JsonNode firstMediaObject = responseContent.get(0);
					assertEquals( "amo_paçoca.jpg", firstMediaObject.get("name").asText() );
					String responseFirstMediaId = firstMediaObject.get("id").asText();
					assertEquals( firstDummyMediaId.toString(), responseFirstMediaId);
					String firstInternalId = firstMediaObject.get("internalId").asText();
					assertEquals( "1", firstInternalId);
					
					JsonNode secondMediaObject = responseContent.get(1);
					assertEquals( "desmotivacional.jpg", secondMediaObject.get("name").asText() );
					String secondFirstMediaId = secondMediaObject.get("id").asText();
					assertEquals( secondDummyMediaId.toString(), secondFirstMediaId);
					String secondInternalId = secondMediaObject.get("internalId").asText();
					assertEquals( "2", secondInternalId);
				});
	}
	
}
