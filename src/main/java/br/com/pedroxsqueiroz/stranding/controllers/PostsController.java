package br.com.pedroxsqueiroz.stranding.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.pedroxsqueiroz.stranding.dtos.PostDto;
import br.com.pedroxsqueiroz.stranding.dtos.PostMediaDto;
import br.com.pedroxsqueiroz.stranding.exception.MediaServiceNotAvailable;
import br.com.pedroxsqueiroz.stranding.exception.PostNotFoundException;
import br.com.pedroxsqueiroz.stranding.models.Post;
import br.com.pedroxsqueiroz.stranding.models.PostMedia;
import br.com.pedroxsqueiroz.stranding.models.User;
import br.com.pedroxsqueiroz.stranding.services.FeedService;
import br.com.pedroxsqueiroz.stranding.services.PostService;

@RestController()
@RequestMapping("/posts")
public class PostsController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FeedService feedService;

	@GetMapping("/feed/{part}")
	@ResponseBody
	public Page<PostDto> feed(@PathVariable("part") int part, Authentication auth)
	{
		User user = (User) auth.getPrincipal();
		
		ModelMapper dtoMapper = new ModelMapper();
		
		return this.feedService
						.getFeedPartOf(user, part)
						.map( post -> dtoMapper.map(post, PostDto.class) );
		
	}
	
	@PostMapping()
	@ResponseBody
	public PostDto create( @RequestBody PostDto post, Authentication auth ) 
	{
		User user = (User) auth.getPrincipal();
		
		Post savedPost = this.postService.add(user, post);
		
		return new ModelMapper().map(savedPost, PostDto.class);
	}
	
	@PostMapping("/{postId}/media")
	@ResponseBody
	public List<PostMediaDto> attachMedia( @PathVariable("postId") String postId, List<MultipartFile> medias ) throws MediaServiceNotAvailable, PostNotFoundException, IOException
	{
		
		Map<String, InputStream> mediasMap = new HashMap<>();
		
		for(MultipartFile currentMedia : medias) 
		{
			mediasMap.put(currentMedia.getOriginalFilename()
						, currentMedia.getInputStream());
		}
		
		List<PostMedia> attachedMedias = this.postService.attachMedias(
																UUID.fromString(postId), 
																mediasMap
															);
		
		return attachedMedias	.stream()
								.map(PostMediaDto::new)
								.collect(Collectors.toList());
	}

}
