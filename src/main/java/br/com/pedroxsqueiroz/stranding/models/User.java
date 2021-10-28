package br.com.pedroxsqueiroz.stranding.models;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Table(name = "stranding_user")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Type(type = "uuid-char")
	@Column(name = "stranding_user_id", columnDefinition="uniqueidentifier")
	@EqualsAndHashCode.Include
	private UUID id;
	
	@Column(name = "stranding_user_name")
	private String name;
	
	@Column(name = "stranding_user_login")
	private String login;
	
	@ManyToMany()
	@JoinTable(name = "stranding_user_x_friends",
				joinColumns = @JoinColumn(name = "stranding_user_id" ),
				inverseJoinColumns = @JoinColumn(name = "friend_id"))
	private List<User> friends;
		
}
