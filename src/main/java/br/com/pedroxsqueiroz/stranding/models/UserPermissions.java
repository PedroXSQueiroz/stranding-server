package br.com.pedroxsqueiroz.stranding.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stranding_user_permission")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public  class UserPermissions implements GrantedAuthority{

	@Id
	@Column(name = "stranding_user_permission_id")
	private UUID id;
	
	@Column(name = "stranding_user_permission_name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "stranding_user_id")
	private User user;
	
	@Override
	public String getAuthority() {
		return this.name;
	}
	
}
