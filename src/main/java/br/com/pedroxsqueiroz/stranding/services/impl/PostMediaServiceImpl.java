package br.com.pedroxsqueiroz.stranding.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pedroxsqueiroz.stranding.models.PostMedia;
import br.com.pedroxsqueiroz.stranding.repository.PostMediaRepository;
import br.com.pedroxsqueiroz.stranding.services.PostMediaService;

@Service
public class PostMediaServiceImpl implements PostMediaService {

	@Autowired
	private PostMediaRepository mediaRepo;
	
	@Override
	public PostMedia save(PostMedia media) {
		return this.mediaRepo.save(media);
	}

}
