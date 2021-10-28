package br.com.pedroxsqueiroz.stranding.services.impl;

import java.util.Base64;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pedroxsqueiroz.stranding.exception.TokenException;
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
