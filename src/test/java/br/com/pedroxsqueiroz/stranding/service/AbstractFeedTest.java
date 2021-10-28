package br.com.pedroxsqueiroz.stranding.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.pedroxsqueiroz.stranding.models.Post;
import br.com.pedroxsqueiroz.stranding.models.User;
import br.com.pedroxsqueiroz.stranding.services.FeedService;
import br.com.pedroxsqueiroz.stranding.services.impl.FeedServiceImpl;

abstract class AbstractFeedTest {

	protected abstract Instant getTopFeedFinalDate();

	protected abstract Instant getTopFeedStartDate();

	protected abstract List<Post> getExpectedPosts();
	
	private List<Post> currentExpectedPosts;
	
	private List<Post> getCurrentExpectedPostsSingleton()
	{
		return Objects.requireNonNullElseGet(currentExpectedPosts, () -> 
			currentExpectedPosts = getExpectedPosts()
		);
	}
	
	protected User getDummyUser() 
	{
		return getMainDummyUser();
	}

	protected List<User> getExpectedFriends()
	{
		return getMainDummyUserAllFriends();
	}


	@Autowired
	protected FeedService feedService = new FeedServiceImpl();
	
	protected void validateRemainingUsers(final Set<User> foundedUsers) {
	
		List<User> expectedFriends = this.getExpectedFriends();
		
		List<User> notFoundedUsers = expectedFriends.stream()
													.filter( Predicate.not(foundedUsers::contains) )
													.collect(Collectors.toList());
		
		assertTrue(notFoundedUsers.isEmpty(), () -> 
				String.format("Not all friends was found, reamining %", 
					String.join(", ", notFoundedUsers.stream()
												.map(User::getName)
												.collect(Collectors.toList())
							) 
						)
				);
		
	}

	protected boolean validateUser(User creator) {
		
		int foundedIndexFriend = this.getExpectedFriends().indexOf(creator);
		boolean friendFounded = foundedIndexFriend != -1;
		
		assertTrue(friendFounded, 
				String.format("Post from %s is no expected, is not a friend", 
					creator.getName() )
			);
		
		return friendFounded;
	}

	protected void validatePost(Post post) {
		
		List<Post> expectedPosts = this.getCurrentExpectedPostsSingleton();
		int foundedIndexPost = expectedPosts.indexOf(post);
		boolean postFounded = foundedIndexPost != -1;
		
		assertTrue(postFounded, 
					String.format("Post %s is no expected, is not from a friend or not on expected range", 
						post.getId().toString() )
				);
		
		assertTrue(post.getCreationDate().isBefore(this.getTopFeedStartDate()));
		assertTrue(post.getCreationDate().isAfter( this.getTopFeedFinalDate()));
		
		expectedPosts.remove(foundedIndexPost);
	
	}

	protected User getMainDummyUser() {
		return User.builder()
				.id(UUID.fromString("c2c15f60-2f0d-11ec-8d3d-0242ac130003"))
				.name("dummy")
				.login("dummy")
				.friends(getExpectedFriends())
				.build();
	}

	protected List<User> getMainDummyUserAllFriends() {
		return Lists.list( 	User.builder()
								.id(UUID.fromString("a3d23d8c-2f11-11ec-8d3d-0242ac130003"))
								.name("dummy_friend")
								.login("dummy_friend")
								.build()
							
							,User.builder()
								.id(UUID.fromString("c5489bdc-2f11-11ec-8d3d-0242ac130003"))
								.name("dummy_friend_1")
								.login("dummy_friend_1")
								.build()
							
							,User.builder()
								.id(UUID.fromString("cff87ff2-2f11-11ec-8d3d-0242ac130003"))
								.name("dummy_friend_2")
								.login("dummy_friend_2")
								.build()
							
							,User.builder()
								.id(UUID.fromString("d995bb10-2f11-11ec-8d3d-0242ac130003"))
								.name("dummy_friend_3")
								.login("dummy_friend_3")
								.build() 
						);
	}

}
