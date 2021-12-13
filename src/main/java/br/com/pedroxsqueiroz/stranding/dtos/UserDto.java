package br.com.pedroxsqueiroz.stranding.dtos;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.pedroxsqueiroz.stranding.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	public UserDto(User user) 
	{
		this.setId(user.getId());
		this.setLogin(user.getLogin());
		this.setName(user.getName());
	}
	
	private UUID id;
	
	private String name;
	
	private String login;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	private List<String> profiles;
	
}
