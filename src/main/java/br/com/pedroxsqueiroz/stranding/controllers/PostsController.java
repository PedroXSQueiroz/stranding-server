package br.com.pedroxsqueiroz.stranding.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedroxsqueiroz.stranding.dtos.PostDto;
import br.com.pedroxsqueiroz.stranding.exception.TokenException;
import br.com.pedroxsqueiroz.stranding.models.User;
import br.com.pedroxsqueiroz.stranding.services.FeedService;
import br.com.pedroxsqueiroz.stranding.services.UserService;

@RestController()
@RequestMapping("/posts")
public class PostsController {

	@Autowired
	private FeedService feedService;

	@GetMapping(name = "/feed/{part}")
	Page<PostDto> feed(@PathVariable("part") int part, Authentication auth) throws TokenException
	{
		User user = (User) auth.getPrincipal();
		
		ModelMapper dtoMapper = new ModelMapper();
		
		return this.feedService
						.getFeedPartOf(user, part)
						.map( post -> dtoMapper.map(post, PostDto.class) );
		
	}

}
