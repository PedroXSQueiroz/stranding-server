package br.com.pedroxsqueiroz.stranding.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.pedroxsqueiroz.stranding.models.Post;
import br.com.pedroxsqueiroz.stranding.models.User;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
@Sql( 	scripts = { "/migrations/V001__starting_schema.sql"
					,"/feedInitialData/initial_posts_and_users.sql" }
		,executionPhase = ExecutionPhase.BEFORE_TEST_METHOD )
@Sql( 	scripts = { "/regrations/V001__droping_schema.sql" }
		,executionPhase = ExecutionPhase.AFTER_TEST_METHOD )
class FeedServiceFirstPartTest extends AbstractFeedTest{
	
	@Override
	protected List<Post> getExpectedPosts()
	{
		return Lists.list( Post.builder()
				.id( UUID.fromString("174d6195-000b-465c-bcda-1ee5ee2da36f"))
				.build()
				
				,Post.builder()
				.id( UUID.fromString("dc4cdb72-e9b2-4327-923d-c511679cae74"))
				.build()
				
				,Post.builder()
				.id( UUID.fromString("7d9c79a6-d785-4e4a-912e-2b05cbaf46bc"))
				.build()
				
				,Post.builder()
				.id( UUID.fromString("23d39885-9565-464f-9e3a-c405a0f9fe59"))
				.build()
				
				,Post.builder()
				.id( UUID.fromString("125adbf0-0bf2-4ea6-8c76-d3b9fcab894f"))
				.build()
				
				,Post.builder()
				.id( UUID.fromString("2ed3c43b-842c-41a9-aa2e-51dc05c2fe07"))
				.build()
				
				,Post.builder()
				.id( UUID.fromString("03429b0d-c371-4e3f-bc54-4498f56c8f51"))
				.build()
				
				,Post.builder()
				.id( UUID.fromString("ac8149ae-84d2-4ced-82b5-acdfca3497b4"))
				.build()
			);
	}
	
	@Override
	protected Instant getTopFeedStartDate() 
	{
		return 	LocalDateTime.parse(
					"2021-06-01T13:00:00" 
					,DateTimeFormatter.ISO_DATE_TIME)
				.atZone(ZoneId.systemDefault())
				.toInstant();
			
	}
	
	@Override
	protected Instant getTopFeedFinalDate() 
	{
		return 	LocalDateTime.parse(
					"2021-06-01T12:30:00" 
					,DateTimeFormatter.ISO_DATE_TIME)
				.atZone(ZoneId.systemDefault())
				.toInstant();
	
	}

	@Test
	void shoulBuildFisrtPartOfFeed()
	{
		final Set<User> foundedUsers = new HashSet<User>();
		
		Page<Post> posts = this.feedService.popFeedOf(this.getDummyUser());
		
		posts.forEach(post -> {
			
			User creator = post.getCreator();
			validateUser(creator); 						
			foundedUsers.add(creator);
			
			validatePost(post);
		});
		
		validateRemainingUsers(foundedUsers);
	}
	
}
