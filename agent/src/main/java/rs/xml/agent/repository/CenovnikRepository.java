package rs.xml.agent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.xml.agent.model.Cenovnik;

@Repository
public interface CenovnikRepository extends JpaRepository<Cenovnik, Long> {
	
	@Query("SELECT c FROM Cenovnik c WHERE c.username = :username")
	List<Cenovnik> findAllFromUser(@Param("username") String username);
	
	@Query("SELECT c FROM Cenovnik c WHERE c.cid = :cid")
	Cenovnik findOneByCid(@Param("cid") String cid);
	
}
