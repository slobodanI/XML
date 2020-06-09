package rs.xml.oglas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rs.xml.oglas.model.Zahtev;

@Repository
public interface ZahtevRepository extends JpaRepository<Zahtev,Long> {
	
	@Query("Select z from Zahtev z WHERE z.status = 'PENDING'")
	List<Zahtev> findPending();
	

}
