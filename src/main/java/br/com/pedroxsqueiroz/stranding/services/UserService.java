package br.com.pedroxsqueiroz.stranding.services;

import org.springframework.security.core.Authentication;

import br.com.pedroxsqueiroz.stranding.exception.TokenException;
import br.com.pedroxsqueiroz.stranding.models.User;

public interface UserService {
	
	User getByLogin(String login);

	User getByToken(String token) throws TokenException;

}
