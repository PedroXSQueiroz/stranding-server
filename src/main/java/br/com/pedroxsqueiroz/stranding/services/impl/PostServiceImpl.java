package br.com.pedroxsqueiroz.stranding.services.impl;

import java.io.InputStream;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pedroxsqueiroz.stranding.dtos.PostDto;
import br.com.pedroxsqueiroz.stranding.exception.MediaServiceNotAvailable;
import br.com.pedroxsqueiroz.stranding.models.Post;
import br.com.pedroxsqueiroz.stranding.models.PostMedia;
import br.com.pedroxsqueiroz.stranding.models.User;
import br.com.pedroxsqueiroz.stranding.repository.PostRepository;
import br.com.pedroxsqueiroz.stranding.services.MediaService;
import br.com.pedroxsqueiroz.stranding.services.PostMediaService;
import br.com.pedroxsqueiroz.stranding.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	public void setPostRepository(PostRepository postRepo) 
	{
		this.postRepo = postRepo;
	}
	
	private PostRepository postRepo;
	
	@Autowired(required = false)
	public void setMediaService(MediaService mediaService) 
	{
		this.mediaService = mediaService;
	}
	
	private MediaService mediaService;
	
	@Autowired
	public void setPostMediaService(PostMediaService postMediaService) 
	{
		this.postMediaService = postMediaService;
	}
	
	private PostMediaService postMediaService;
	
	@Override
	public Post add(User user, PostDto postDto) {
		
		Post post = Post.builder()
			.content(postDto.getContent())
			.creationDate(Instant.now())
			.creator(user)
			.build();
		
		return this.postRepo.save(post);
	}

	@Override
	public List<PostMedia> attachMedias(UUID postId, Map<String, InputStream> medias) throws MediaServiceNotAvailable {
		
		if(this.postMediaService == null)
		{
			throw new MediaServiceNotAvailable();
		}
		
		Optional<Post> postQuey = this.postRepo.findById(postId);
		
		if(postQuey.isEmpty()) 
		{
			return null;
		}
		
		Post post = postQuey.get();
		
		return medias.keySet()
				.stream()
				.map( mediaName -> {
					
					String internalId = this.mediaService.set(medias.get(mediaName));
					
					PostMedia postMedia = PostMedia.builder()
							.post(post)
							.name(mediaName)
							.internalId(internalId)
							.build();
					
					return this.postMediaService.save(postMedia);
				})
				.collect(Collectors.toList());
		
	}

	private void save(PostMedia postmedia1) {
	}

}
