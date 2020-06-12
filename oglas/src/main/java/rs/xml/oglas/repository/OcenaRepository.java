package rs.xml.oglas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.xml.oglas.model.Ocena;


@Repository
public interface OcenaRepository extends JpaRepository<Ocena, Long>{

//	@Query("SELECT o FROM Ocena WHERE USERNAME_KOGA = :username")
//	List<Ocena> findMyOcene(@Param("username") String username);
//	
}
