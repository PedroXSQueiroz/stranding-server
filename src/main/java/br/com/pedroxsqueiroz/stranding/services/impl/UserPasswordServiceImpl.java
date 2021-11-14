package br.com.pedroxsqueiroz.stranding.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pedroxsqueiroz.stranding.models.UserPassword;
import br.com.pedroxsqueiroz.stranding.repository.UserPasswordRepository;
import br.com.pedroxsqueiroz.stranding.services.UserPasswordService;

@Service
public class UserPasswordServiceImpl implements UserPasswordService {
	
	@Autowired
	private UserPasswordRepository passwordRepo;
	
	@Override
	public String getActiveByLogin(String login) {
		List<String> passwords = this.passwordRepo.getByLogin(login);
		
		if(passwords.isEmpty()) 
		{
			return null;
		}
		
		return passwords.get(0);
	}

	@Override
	public UserPassword save(UserPassword password) {
		return this.passwordRepo.save(password);
	}

}
