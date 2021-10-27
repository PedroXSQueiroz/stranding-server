package br.com.pedroxsqueiroz.stranding.models;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
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
@Table(name = "post")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode( onlyExplicitlyIncluded = true )
public class Post implements Comparable<Post>{

	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Type(type = "uuid-char")
	@Column(name = "post_id", columnDefinition="uniqueidentifier")
	@EqualsAndHashCode.Include
	private UUID id;
	
	@Column(name = "post_content")
	private String content;
	
	@ElementCollection()
	@CollectionTable(name = "post_url_images"
					,joinColumns = @JoinColumn(name = "post_id"))
	private List<String> urlImages;

	@ManyToOne
	@JoinColumn(name = "stranding_user_id")
	private User creator;
	
	@Column(name = "creation_date")
	private Instant creationDate;

	@Override
	public int compareTo(Post o) {
		Instant otherCreationDate = o.getCreationDate();
		Instant thisCreationDate = this.getCreationDate();
		return otherCreationDate.compareTo(thisCreationDate);
	}
	
}
