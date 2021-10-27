package br.com.pedroxsqueiroz.stranding.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.pedroxsqueiroz.stranding.models.Post;
import br.com.pedroxsqueiroz.stranding.models.User;
import br.com.pedroxsqueiroz.stranding.repository.PostRepository;
import br.com.pedroxsqueiroz.stranding.services.FeedService;

@Service
public class FeedServiceImpl implements FeedService {

	@Autowired
	private PostRepository postRepository;
	
	@Value("${maxPostsSectionUser}")
	public void setMaxPostsSectionUser(int max) 
	{
		this.maxPostsSectionUser = max;
	}
	
	private int maxPostsSectionUser;
	
	@Value("${postsPageSize}")
	public void setPostsPageSize(int size) 
	{
		this.postsPageSize = size;
	}
	
	private int postsPageSize;
	
	@Override
	public Page<Post> popFeedOf(User dummyUser) {
		
		int currentPostsPart = 0;
		return getFeedPostPage(dummyUser, currentPostsPart);
	}

	private Page<Post> getFeedPostPage(User dummyUser, int currentPostsPart) {
		int postOfUserSection = this.getUserSection(currentPostsPart);
		
		List<Post> posts = dummyUser.getFriends()
						.stream()
						.flatMap(friend -> 
							
							this.postRepository.findAll( (root, query, cb) -> 
								cb.equal( root.get("creator"), friend )
								,PageRequest.of(
									postOfUserSection, maxPostsSectionUser, 
									Sort.by( Sort.Order.desc("creationDate") ) 
								) 
							).stream()
						
						).sorted()
						.collect( Collectors.toList() );
		
		return new PageImpl<Post>( 
							posts, 
							PageRequest.of(currentPostsPart, this.postsPageSize) 
							, Integer.MAX_VALUE 
						);
	}

	private int getUserSection(int currentPostsPage) {
		return currentPostsPage;
	}

	@Override
	public Page<Post> getFeedPartOf(User dummyUser, int currentPostPart) {
		return this.getFeedPostPage(dummyUser, currentPostPart);
	}

}
