package br.com.pedroxsqueiroz.stranding.services.impl;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;

import br.com.pedroxsqueiroz.stranding.dtos.UserDto;
import br.com.pedroxsqueiroz.stranding.models.User;
import br.com.pedroxsqueiroz.stranding.models.UserPassword;
import br.com.pedroxsqueiroz.stranding.models.UserPermissions;
import br.com.pedroxsqueiroz.stranding.repository.UserRepository;
import br.com.pedroxsqueiroz.stranding.services.UserPasswordService;
import br.com.pedroxsqueiroz.stranding.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	public void setUserRepository(UserRepository repo) 
	{
		this.userRepository = repo;
	}
	
	private UserRepository userRepository;
	
	@Autowired
	public void setUserPasswordService( UserPasswordService userPasswordService ) 
	{
		this.passwordService = userPasswordService;
	}
	
	private UserPasswordService passwordService;
	
	@Override
	public User getByLogin(String login) {
		return this.userRepository.findOneByLogin(login);
	}

	@Override
	public User create(UserDto userDto) {
		
		User user = this.userRepository	.save(User.builder()
										.name(userDto.getName())
										.login(userDto.getLogin())
										.profiles( Set.of( UserPermissions.builder().name("default").build() ) )
										.build());
		
		this.passwordService.save(UserPassword.builder()
												.creationDate(Instant.now())
												.user(user)
												.hash(Hashing	.sha256()
																.hashString(userDto.getPassword(), StandardCharsets.UTF_8)
																.toString()
												).build());
		
		return user;
	}

	@Override
	public List<User> addFriends(UUID userId, List<UUID> friendsIds) {
		
		Optional<User> userQuery = this.userRepository.findById(userId);
		
		List<User> newFriends = friendsIds	.stream()
									.map(userRepository::findById)
									.filter(Optional::isPresent)
									.map( queryUser -> queryUser.get())
									.collect(Collectors.toList());
		
		if(userQuery.isPresent()) 
		{
			User user = userQuery.get();
			List<User> friends = user.getFriends();
			
			List<User> remainingFriends = newFriends.stream()
													.filter(Predicate.not( friends::contains ))
													.collect(Collectors.toList());
			
			remainingFriends.forEach( newFriend -> {
				newFriend.getFriends().add(user);
				this.userRepository.save(newFriend);
			});
			
			user.getFriends().addAll(remainingFriends);
			
			this.userRepository.save(user);
			
			return user.getFriends();
		
		}
		
		return null;
	}

}
