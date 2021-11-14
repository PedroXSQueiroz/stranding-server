package br.com.pedroxsqueiroz.stranding.models;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
public class User implements UserDetails{

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
	
	@OneToMany(mappedBy = "user")
	@OrderBy("creationDate")
	private List<UserPassword> passwords;
	
	@OneToMany(mappedBy = "user")
	private Set<UserPermissions> profiles;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.profiles;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.getPasswords().get(0).getHash();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
		
}
