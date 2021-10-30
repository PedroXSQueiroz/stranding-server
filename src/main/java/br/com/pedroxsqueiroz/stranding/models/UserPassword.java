package br.com.pedroxsqueiroz.stranding.models;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "stranding_user_password")
@Data
public class UserPassword {
	
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Type(type = "uuid-char")
	@Column(name = "stranding_user_password_id", columnDefinition="uniqueidentifier")
	@EqualsAndHashCode.Include
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "hash")
	private String hash;
	
	@Column(name = "creation_date")
	private Instant creationDate;
	
}
