package br.com.pedroxsqueiroz.stranding.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.pedroxsqueiroz.stranding.models.Post;

public interface PostRepository 
					extends JpaRepository<Post, UUID>
							,JpaSpecificationExecutor<Post>{

}
