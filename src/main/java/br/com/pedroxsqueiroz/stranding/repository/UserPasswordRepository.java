package br.com.pedroxsqueiroz.stranding.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.pedroxsqueiroz.stranding.models.UserPassword;

@Repository
public interface UserPasswordRepository extends JpaRepository<UserPassword, UUID> {

	@Query("SELECT p.hash FROM UserPassword p JOIN p.user u WHERE u.login = :login ORDER BY p.creationDate DESC")
	List<String> getByLogin( @Param("login") String login);

}
