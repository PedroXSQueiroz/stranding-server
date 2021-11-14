package br.com.pedroxsqueiroz.stranding.dtos;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private UUID id;
	
	private String name;
	
	private String login;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
}
