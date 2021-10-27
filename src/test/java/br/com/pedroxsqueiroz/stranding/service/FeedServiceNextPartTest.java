package br.com.pedroxsqueiroz.stranding.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;

import br.com.pedroxsqueiroz.stranding.models.Post;
import br.com.pedroxsqueiroz.stranding.models.User;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class FeedServiceNextPartTest extends AbstractFeedTest{
	

	@Override
	protected List<Post> getExpectedPosts()
	{	
		return Lists.list( Post.builder()
				.id( UUID.fromString("39c1062a-3790-4fcc-b394-9c199dd32e6f"))
				.build()
				
				,Post.builder()
				.id( UUID.fromString("d0de1083-c1ef-4536-8b86-0592f20d3bf9"))
				.build()
				
				,Post.builder()
				.id( UUID.fromString("8863ca30-8d77-401a-8ad7-42e66ec3b88a"))
				.build()
				
				,Post.builder()
				.id( UUID.fromString("da77d854-b63f-48af-9aa1-becda5b574f6"))
				.build()
				
				,Post.builder()
				.id( UUID.fromString("663f44f3-c0f7-48f6-ab00-82f32eab6738"))
				.build()
				
				,Post.builder()
				.id( UUID.fromString("32d635e7-e71d-4c6a-b6a1-b2ed3d78102d"))
				.build()
				
				,Post.builder()
				.id( UUID.fromString("f6aaa950-3643-4054-a160-1d70cf87094a"))
				.build()
				
				,Post.builder()
				.id( UUID.fromString("8d7ffd80-77b7-47df-9056-c5d3b169ac34"))
				.build()
			);
	}
	
	@Override
	protected Instant getTopFeedStartDate() 
	{
		return 	LocalDateTime.parse(
					"2021-05-30T13:00:00" 
					,DateTimeFormatter.ISO_DATE_TIME)
				.atZone(ZoneId.systemDefault())
				.toInstant();
			
	}
	
	@Override
	protected Instant getTopFeedFinalDate() 
	{
		return 	LocalDateTime.parse(
					"2021-05-01T12:30:00" 
					,DateTimeFormatter.ISO_DATE_TIME)
				.atZone(ZoneId.systemDefault())
				.toInstant();
	
	}
	
	@Test
	public void shoulBuildNextPartOfFeed()
	{
		final Set<User> foundedUsers = new HashSet<User>();
		
		Page<Post> posts = this.feedService.getFeedPartOf(this.getDummyUser(), 1);
		
		posts.forEach(post -> {
			
			User creator = post.getCreator();
			validateUser(creator); 						
			foundedUsers.add(creator);
			
			validatePost(post);
		});
		
		validateRemainingUsers(foundedUsers);
	}
	
}
