package br.com.pedroxsqueiroz.stranding.services.impl;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pedroxsqueiroz.stranding.dtos.PostDto;
import br.com.pedroxsqueiroz.stranding.models.Post;
import br.com.pedroxsqueiroz.stranding.models.User;
import br.com.pedroxsqueiroz.stranding.repository.PostRepository;
import br.com.pedroxsqueiroz.stranding.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepo;
	
	@Override
	public Post add(User user, PostDto postDto) {
		
		Post post = Post.builder()
			.content(postDto.getContent())
			.creationDate(Instant.now())
			.creator(user)
			.build();
		
		return this.postRepo.save(post);
	}

}
