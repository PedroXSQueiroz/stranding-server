package br.com.pedroxsqueiroz.stranding.dtos;

import java.util.UUID;

import br.com.pedroxsqueiroz.stranding.models.PostMedia;
import lombok.Getter;

@Getter
public class PostMediaDto {
	
	public PostMediaDto(PostMedia media) 
	{
		this.id = media.getId();
		this.internalId =  media.getInternalId();
		this.name = media.getName();
	}
	
	private UUID id;
	
	private String internalId;
	
	private String name;
	
}
