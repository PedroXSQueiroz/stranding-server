package br.com.pedroxsqueiroz.stranding.services;

import org.springframework.data.domain.Page;

import br.com.pedroxsqueiroz.stranding.models.Post;
import br.com.pedroxsqueiroz.stranding.models.User;

public interface FeedService {

	Page<Post> popFeedOf(User dummyUser);

	Page<Post> getFeedPartOf(User dummyUser, int i);
	
}
