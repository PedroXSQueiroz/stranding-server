package br.com.pedroxsqueiroz.stranding.dtos;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
	
	private String content;
	
	private UserDto creator;
	
	private Instant creationDate;
	
	private List<String> urlImages;
	
}
