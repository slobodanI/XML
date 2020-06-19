package rs.xml.oglas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import feign.Param;
import rs.xml.oglas.model.Oglas;
import rs.xml.oglas.model.Zahtev;

@Repository
public interface ZahtevRepository extends JpaRepository<Zahtev,Long> {
	
	@Query("Select z from Zahtev z WHERE z.status = 'PENDING'")
	List<Zahtev> findPending();

	@Query("SELECT z FROM Zahtev z WHERE z.podnosilacUsername = :username")
	List<Zahtev> findMyZahtevi(@Param("username") String username);

	@Query("SELECT z FROM Zahtev z WHERE z.username = :username")
	List<Zahtev> findZahteviForMe(@Param("username") String username);
	
	@Query("SELECT z FROM Zahtev z WHERE z.zid = :zid")
	Zahtev findOglasByZid(@Param("zid") String zid);

}
