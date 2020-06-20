package rs.xml.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.xml.chat.model.Poruka;


@Repository
public interface PorukaRepository extends JpaRepository<Poruka, Long>{
	
	@Query("SELECT p FROM Poruka p WHERE p.pid = :pid")
	Poruka findPorukaByPid(@Param("pid") String pid);
	
}
