package br.com.pedroxsqueiroz.stranding.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedroxsqueiroz.stranding.dtos.UserDto;
import br.com.pedroxsqueiroz.stranding.models.User;
import br.com.pedroxsqueiroz.stranding.services.UserService;

@RestController()
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping()
	@ResponseBody
	public UserDto createUser( @RequestBody UserDto userDto) 
	{
		User created = this.userService.create( userDto );
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings( new PropertyMap<User, UserDto>(){

			@Override
			protected void configure() {
				this.skip(destination.getPassword());
			}
			
		});
		
		return modelMapper.map(created, UserDto.class);
	}
	
	@PutMapping("/friends")
	@ResponseBody
	public List<String> addFriends(@RequestBody List<String> friendsIdsStr, @AuthenticationPrincipal User user)
	{
		
		List<UUID> friendsIds = friendsIdsStr.stream()
											.map(UUID::fromString)
											.collect(Collectors.toList());
		
		return this.userService.addFriends(user.getId(), friendsIds)
								.stream()
								.map( User::getId )
								.map( UUID::toString )
								.collect(Collectors.toList());
	}
	
}
