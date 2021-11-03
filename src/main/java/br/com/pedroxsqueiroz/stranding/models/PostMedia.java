package br.com.pedroxsqueiroz.stranding.models;

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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Table(name = "post_media")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PostMedia {

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Type(type = "uuid-char")
	@Column(name = "post_media_id", columnDefinition="uniqueidentifier")
	@EqualsAndHashCode.Include
	private UUID id;
	
	@JoinColumn(name = "post_id")
	@ManyToOne
	private Post post;
	
	@Column(name = "media_name")
	private String name;
	
	@Column(name = "internal_id")
	private String internalId;
	
}
