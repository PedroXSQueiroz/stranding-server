package br.com.pedroxsqueiroz.stranding.services;

import br.com.pedroxsqueiroz.stranding.models.User;

public interface UserService {
	
	User getByLogin(String login);
	
}
