package br.com.pedroxsqueiroz.stranding.services.impl;




import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import br.com.pedroxsqueiroz.stranding.models.User;
import br.com.pedroxsqueiroz.stranding.repository.UserRepository;
import br.com.pedroxsqueiroz.stranding.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	public void setUserRepository(UserRepository repo) 
	{
		this.userRepository = repo;
	}
	
	private UserRepository userRepository;
	
	@Override
	public User getByLogin(String login) {
		return this.userRepository.findOneByLogin(login);
	}

}
