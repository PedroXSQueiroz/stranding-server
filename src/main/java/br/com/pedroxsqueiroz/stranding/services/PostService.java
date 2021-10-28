package br.com.pedroxsqueiroz.stranding.services;

import br.com.pedroxsqueiroz.stranding.dtos.PostDto;
import br.com.pedroxsqueiroz.stranding.models.Post;
import br.com.pedroxsqueiroz.stranding.models.User;

public interface PostService {

	Post add(User user, PostDto post);
	
}
