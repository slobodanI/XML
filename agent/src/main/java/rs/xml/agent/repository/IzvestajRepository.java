package rs.xml.agent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.xml.agent.model.Izvestaj;

@Repository
public interface IzvestajRepository extends JpaRepository<Izvestaj, Long> {
	
	@Query("Select i from Izvestaj i WHERE i.zahtevId = :zahtevId")
	List<Izvestaj> findIzvestajZahteva(@Param("zahtevId") Long zahtevId);
	
	
}
