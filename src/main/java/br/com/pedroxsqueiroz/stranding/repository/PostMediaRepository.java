package br.com.pedroxsqueiroz.stranding.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pedroxsqueiroz.stranding.models.PostMedia;

@Repository
public interface PostMediaRepository extends JpaRepository<PostMedia, UUID> {

}
