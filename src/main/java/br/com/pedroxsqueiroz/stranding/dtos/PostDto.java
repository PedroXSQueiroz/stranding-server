package br.com.pedroxsqueiroz.stranding.dtos;

import java.time.Instant;

import lombok.Data;

@Data
public class PostDto {
	
	private String content;
	
	private UserDto creator;
	
	private Instant creationDate;
	
}
