package br.com.pedroxsqueiroz.stranding.services;

import java.util.List;
import java.util.UUID;

import br.com.pedroxsqueiroz.stranding.dtos.UserDto;
import br.com.pedroxsqueiroz.stranding.models.User;

public interface UserService {
	
	User getByLogin(String login);

	User create(UserDto userDto);

	List<User> addFriends(UUID userId, List<UUID> friendsIds);
	
}
