package br.com.pedroxsqueiroz.stranding.services;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.com.pedroxsqueiroz.stranding.dtos.PostDto;
import br.com.pedroxsqueiroz.stranding.exception.MediaServiceNotAvailable;
import br.com.pedroxsqueiroz.stranding.models.Post;
import br.com.pedroxsqueiroz.stranding.models.PostMedia;
import br.com.pedroxsqueiroz.stranding.models.User;

public interface PostService {

	Post add(User user, PostDto post);

	List<PostMedia> attachMedias(UUID fromString, Map<String, InputStream> medias) throws MediaServiceNotAvailable;
	
}
