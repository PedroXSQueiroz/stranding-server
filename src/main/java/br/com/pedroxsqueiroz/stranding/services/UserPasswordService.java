package br.com.pedroxsqueiroz.stranding.services;

import br.com.pedroxsqueiroz.stranding.models.UserPassword;

public interface UserPasswordService {

	String getActiveByLogin(String login);

	UserPassword save(UserPassword build);
	
}
