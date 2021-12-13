package br.com.pedroxsqueiroz.stranding.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedroxsqueiroz.stranding.dtos.UserDto;
import br.com.pedroxsqueiroz.stranding.models.User;
import br.com.pedroxsqueiroz.stranding.models.UserPermissions;
import br.com.pedroxsqueiroz.stranding.services.UserService;

@CrossOrigin("*")
@RestController()
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping()
	@ResponseBody
	public UserDto createUser(@RequestBody UserDto userDto) {
		
		User created = this.userService.create(userDto);
		
		ModelMapper modelMapper = new ModelMapper();
		
		TypeMap<User, UserDto> typeMapper = modelMapper.createTypeMap(User.class, UserDto.class);
		
		TypeMap<User, UserDto> userMapper = typeMapper.addMappings( 
				mapper ->
					mapper.using(
						new AbstractConverter<List<UserPermissions>, List<String>> () {

							@Override
							protected List<String> convert(List<UserPermissions> source) {
								return source.stream().map(UserPermissions::getName).collect(Collectors.toList());
							}
							
						}
						
					)
				).addMappings(new PropertyMap<User, UserDto>() {

					@Override
					protected void configure() {
						this.skip( this.destination.getPassword() );
					}
				
				});
		
		return userMapper .map(created);
		
	}

	@PutMapping("/friends")
	@ResponseBody
	public List<String> addFriends(@RequestBody List<String> friendsIdsStr, @AuthenticationPrincipal User user) {

		List<UUID> friendsIds = friendsIdsStr.stream().map(UUID::fromString).collect(Collectors.toList());

		return this.userService.addFriends(user.getId(), friendsIds).stream().map(User::getId).map(UUID::toString)
				.collect(Collectors.toList());
	}

}
